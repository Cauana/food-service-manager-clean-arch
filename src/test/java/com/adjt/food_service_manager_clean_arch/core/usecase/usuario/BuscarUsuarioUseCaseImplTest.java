package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarUsuarioUseCaseImpl buscarUsuarioUseCaseImpl;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .id(1001L)
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf("12345678900")
                .restaurantes(Collections.emptyList())
                .build();
    }

    @Test
    @DisplayName("Deve buscar e retornar o usuário encontrado após uma busca por id")
    void deveBuscarUsuarioPorId() {
        Long id = 1001L;

        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.of(usuarioMock));

        Usuario usuarioEncontrado = buscarUsuarioUseCaseImpl.buscarUsuario(id);

        assertNotNull(usuarioEncontrado);
        assertEquals(id, usuarioEncontrado.getId());
        assertEquals(usuarioMock.getNome(), usuarioEncontrado.getNome());

        verify(usuarioGateway, times(1)).buscarPorId(id);
    }


    @Test
    @DisplayName("Deve tentar buscar um usuário não cadastrado e lançar uma exceção quando ele não for encontrado.")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        Long id = 10002L;
        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,() -> buscarUsuarioUseCaseImpl.buscarUsuario(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Usuário Não Encontrado\"", exception.getMessage());

        verify(usuarioGateway, times(1)).buscarPorId(id);
    }

}