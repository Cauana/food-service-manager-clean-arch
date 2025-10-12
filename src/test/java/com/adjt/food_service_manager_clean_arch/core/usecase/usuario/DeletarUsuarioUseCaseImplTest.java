package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DeletarUsuarioUseCaseImpl deletarUsuarioUseCase;

    private final Long usuarioId = 1L;
    private final Long usuarioFake = 99L;
    private Usuario usuarioExistente;

    @BeforeEach
    void setUp() {
        usuarioExistente = new Usuario(
                usuarioId,
                "Maria de Souza",
                "maria.souza@teste.com",
                "09876543211",
                "maria.login",
                "senha456",
                "DONO_RESTAURANTE",
                Collections.emptyList()
        );
    }

    @Test
    @DisplayName("Deve deletar o usuário com sucesso quando encontrado")
    void deveDeletarQuandoUsuarioEncontrado() {

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExistente));
        doNothing().when(usuarioGateway).deletar(usuarioExistente);

        deletarUsuarioUseCase.deletarUsuario(usuarioId);

        verify(usuarioGateway, times(1)).buscarPorId(usuarioId);
        verify(usuarioGateway, times(1)).deletar(usuarioExistente);
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o usuário não é encontrado")
    void deveLancarNotFoundQuandoUsuarioNaoEncontrado() {

        when(usuarioGateway.buscarPorId(usuarioFake)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarUsuarioUseCase.deletarUsuario(usuarioFake);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Usuário não encontrado.\"", exception.getMessage());

        verify(usuarioGateway, times(1)).buscarPorId(usuarioFake);
        verify(usuarioGateway, never()).deletar(any());
    }
}