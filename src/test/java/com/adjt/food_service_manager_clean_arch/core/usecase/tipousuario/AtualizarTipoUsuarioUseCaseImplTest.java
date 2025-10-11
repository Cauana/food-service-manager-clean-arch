package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AtualizarTipoUsuarioUseCaseImplTest {
    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private AtualizarTipoUsuarioUseCaseImpl atualizarTipoUsuarioUseCaseImpl;

    private final Long tipoUsuarioId = 1L;
    private CriarTipoUsuarioDto novoTipoUsuarioDto;
    private TipoUsuario tipoUsuarioExistente;
    private TipoUsuario tipoUsuarioAtualizado;

    @BeforeEach
    void setUp() {

        novoTipoUsuarioDto = new CriarTipoUsuarioDto("DONO_RESTAURANTE", "Usuário do tipo dono do estabelecimento");
        tipoUsuarioExistente = new TipoUsuario(2L, "CLIENTE", "Usuário do tipo cliente");
        tipoUsuarioAtualizado = new TipoUsuario(tipoUsuarioId, "DONO_RESTAURANTE", "Usuário do tipo dono do estabelecimento");

    }

    @Test
    @DisplayName("Deve atualizar o tipo de usuário com sucesso e retornar o objeto atualizado")
    void deveAtualizarTipoUsuarioComSucesso() {

        when(tipoUsuarioGateway.buscarPorId(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuarioExistente));

        when(tipoUsuarioGateway.salvar(any(TipoUsuario.class))).thenReturn(tipoUsuarioAtualizado);

        TipoUsuario resultado = atualizarTipoUsuarioUseCaseImpl.atualizar(tipoUsuarioId, novoTipoUsuarioDto);

        assertNotNull(resultado);
        assertEquals(tipoUsuarioId, resultado.getId());

        assertEquals(novoTipoUsuarioDto.getNome(), resultado.getNome());
        assertEquals(novoTipoUsuarioDto.getDescricao(), resultado.getDescricao());

        verify(tipoUsuarioGateway, times(1)).buscarPorId(tipoUsuarioId);
        verify(tipoUsuarioGateway, times(1)).salvar(any(TipoUsuario.class));

        assertEquals(novoTipoUsuarioDto.getNome(), tipoUsuarioExistente.getNome());
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o tipo usuário não é encontrado")
    void deveLancarNotFoundQuandoTipoUsuarioNaoEncontrado() {

        Long idNaoExistente = 99L;
        when(tipoUsuarioGateway.buscarPorId(idNaoExistente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarTipoUsuarioUseCaseImpl.atualizar(idNaoExistente, novoTipoUsuarioDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Tipo de usuário com id 99 não encontrado.\"", exception.getMessage());

        verify(tipoUsuarioGateway, times(1)).buscarPorId(idNaoExistente);
        verify(tipoUsuarioGateway, never()).criarTipoUsuario(any());
    }
}