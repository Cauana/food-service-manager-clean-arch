package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private BuscarTipoUsuarioUseCaseImpl buscarTipoUsuarioUseCaseImpl;

    private TipoUsuario tipoUsuarioMock;

    @BeforeEach
    void setUp() {
        tipoUsuarioMock = TipoUsuario.builder()
                .id(1001L)
                .nome("CLIENTE")
                .descricao("Usuario do tipo Cliente")
                .build();
    }

    @Test
    @DisplayName("Deve buscar e retornar o tipo de usuário encontrado após uma busca por id")
    void deveBuscarTipoUsuarioPorId() {
        Long id = 1001L;

        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.of(tipoUsuarioMock));

        TipoUsuario response = buscarTipoUsuarioUseCaseImpl.buscarPorId(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(tipoUsuarioMock.getNome(), response.getNome());

        verify(tipoUsuarioGateway, times(1)).buscarPorId(id);
    }


    @Test
    @DisplayName("Deve tentar buscar um tipo de usuário não cadastrado e lançar uma exceção quando ele não for encontrado.")
    void deveLancarExcecaoQuandoTipoUsuarioNaoForEncontrado() {
        Long id = 10002L;
        when(tipoUsuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,() -> buscarTipoUsuarioUseCaseImpl.buscarPorId(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Tipo de usuário com id 10002 não encontrado.\"", exception.getMessage());

        verify(tipoUsuarioGateway, times(1)).buscarPorId(id);
    }
}