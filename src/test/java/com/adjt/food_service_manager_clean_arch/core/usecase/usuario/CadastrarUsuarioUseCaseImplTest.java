package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.validation.CpfValidator;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCaseImpl;

    private Usuario usuarioMock;
    private static final String CPF_VALIDO = "18065259090";
    private static final String CPF_INVALIDO = "00100200344";

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf(CPF_VALIDO)
                .restaurantes(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("Deve criar um novo usuario")
    void deveCriarUmNovoUsuario() {

        when(usuarioGateway.criar(any(Usuario.class))).thenReturn(usuarioMock);

        var usuarioCriado = cadastrarUsuarioUseCaseImpl.criarUsuario(usuarioDTO());

        assertNotNull(usuarioCriado);
        assertEquals(usuarioMock.getNome(), usuarioCriado.getNome());
        assertEquals(Usuario.class, usuarioCriado.getClass());

    }

    public CriarUsuarioDto usuarioDTO() {
        return CriarUsuarioDto.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf(CPF_VALIDO)
                .build();
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar inserir um usuário com cpf inválido ")
    void deveLancarExcecaoQuandoCpfForInvalido() {

        try (MockedStatic<CpfValidator> mocked = mockStatic(CpfValidator.class)) {
            mocked.when(() -> CpfValidator.isValid(anyString())).thenReturn(false);
            usuarioDTO().setCpf(CPF_INVALIDO);

            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                cadastrarUsuarioUseCaseImpl.criarUsuario(usuarioDTO());
            });

            assertEquals("400 BAD_REQUEST \"CPF inválido\"", exception.getMessage());

            verify(usuarioGateway, never()).criar(any());
        }
    }
}
