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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AtualizarUsuarioUseCaseImpl atualizarUsuarioUseCase;

    private Usuario usuarioMock;

    private final Long usuarioId = 1L;
    private CriarUsuarioDto novosDadosDTO;
    private Usuario usuarioExistente;
    private Usuario usuarioAtualizado;

    @BeforeEach
    void setUp() {

        novosDadosDTO = new CriarUsuarioDto(
                "João da Silva",
                "joao.silva@teste.com",
                "12345678900",
                "joao.login",
                "senha123",
                "CLIENTE"
        );

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

        usuarioAtualizado = new Usuario(
                1L,
                "João da Silva",
                "joao.silva@teste.com",
                "12345678900",
                "joao.login",
                "senha123",
                "CLIENTE",
                Collections.emptyList()
        );

    }

    @Test
    @DisplayName("Deve atualizar o usuário com sucesso e retornar o objeto atualizado")
    void deveAtualizarUsuarioComSucesso() {

        when(usuarioGateway.buscarPorId(usuarioId)).thenReturn(Optional.of(usuarioExistente));

        when(usuarioGateway.criar(any(Usuario.class))).thenReturn(usuarioAtualizado);

        Usuario resultado = atualizarUsuarioUseCase.atualizarUsuario(usuarioId, novosDadosDTO);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());

        assertEquals(novosDadosDTO.getNome(), resultado.getNome());
        assertEquals(novosDadosDTO.getEmail(), resultado.getEmail());
        assertEquals(novosDadosDTO.getTipoUsuario(), resultado.getTipoUsuario());

        verify(usuarioGateway, times(1)).buscarPorId(usuarioId);
        verify(usuarioGateway, times(1)).criar(usuarioAtualizado);

        assertEquals(novosDadosDTO.getNome(), usuarioExistente.getNome());
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o usuário não é encontrado")
    void deveLancarNotFoundQuandoUsuarioNaoEncontrado() {

        Long idNaoExistente = 99L;
        when(usuarioGateway.buscarPorId(idNaoExistente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarUsuarioUseCase.atualizarUsuario(idNaoExistente, novosDadosDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Usuário não encontrado.\"", exception.getMessage());

        verify(usuarioGateway, times(1)).buscarPorId(idNaoExistente);
        verify(usuarioGateway, never()).criar(any());
    }
}