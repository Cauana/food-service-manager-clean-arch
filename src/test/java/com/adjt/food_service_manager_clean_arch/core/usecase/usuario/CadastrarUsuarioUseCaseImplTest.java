package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCaseImpl;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf("123.456.789-00")
                .restaurantes(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("Deve mapear CriarUsuarioDto para Usuario Corretamente")
    void deveriaCriarUsuario() {

        when(usuarioGateway.criar(any(Usuario.class))).thenReturn(usuarioMock);

        var usuarioCriado = cadastrarUsuarioUseCaseImpl.criarUsuario(usuarioDTO());

        assertNotNull(usuarioCriado);
        assertEquals( usuarioMock.getNome(), usuarioCriado.getNome());
        assertEquals(Usuario.class, usuarioCriado.getClass());

    }

    public CriarUsuarioDto usuarioDTO(){
        return CriarUsuarioDto.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf("123.456.789-00")
                .build();
    }

}