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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private DeletarTipoUsuarioUseCaseImpl deletarTipoUsuarioUseCaseImpl;

    private final Long tipoUsuarioId = 1L;
    private final Long tipoUsuarioFake = 99L;
    private TipoUsuario tipoUsuarioExistente;

    @BeforeEach
    void setUp() {
        tipoUsuarioExistente = new TipoUsuario(
                tipoUsuarioId,
                "CLIENTE",
                "Tipo Usuario Cliente"
        );
    }

    @Test
    @DisplayName("Deve deletar o tipo de usuário com sucesso quando encontrado")
    void deveDeletarQuandoTipoUsuarioEncontrado() {

        when(tipoUsuarioGateway.buscarPorId(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuarioExistente));
        doNothing().when(tipoUsuarioGateway).deletar(tipoUsuarioExistente);

        deletarTipoUsuarioUseCaseImpl.deletar(tipoUsuarioId);

        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuarioId);
        verify(tipoUsuarioGateway, times(1)).deletar(tipoUsuarioExistente);
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o tipo de usuário não é encontrado")
    void deveLancarNotFoundQuandoTipoUsuarioNaoEncontrado() {

        when(tipoUsuarioGateway.buscarPorId(tipoUsuarioFake)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarTipoUsuarioUseCaseImpl.deletar(tipoUsuarioFake);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Tipo de usuário com id 99 não encontrado.\"", exception.getMessage());

        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuarioFake);
        verify(tipoUsuarioGateway, never()).deletar(any());
    }
}