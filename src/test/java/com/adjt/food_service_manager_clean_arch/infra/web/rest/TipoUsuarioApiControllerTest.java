package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioApiControllerTest {

    @Mock
    private TipoUsuarioUseCaseImpl tipoUsuarioUseCaseImpl;

    @Mock
    private ListarTodosTiposUsuarioUseCaseImpl listarTodosTiposUsuarioUseCaseImpl;

    @Mock
    private BuscarTipoUsuarioUseCaseImpl  buscarTipoUsuarioUseCaseImpl;

    @Mock
    private AtualizarTipoUsuarioUseCaseImpl atualizarTipoUsuarioUseCaseImpl;

    @Mock
    private DeletarTipoUsuarioUseCaseImpl deletarTipoUsuarioUseCaseImpl;

    @InjectMocks
    private TipoUsuarioApiController tipoUsuarioApiController;

    private CriarTipoUsuarioDto criarTipoUsuarioDto;
    private TipoUsuario tipoUsuarioCriado;
    private TipoUsuario tipoUsuarioEncontrado;
    private TipoUsuario tipoUsuarioAtualizado;
    private Long tipoUsuarioId;

    @BeforeEach
    void setUp() {
        tipoUsuarioId = 10L;
        criarTipoUsuarioDto = new CriarTipoUsuarioDto("DONO_RESTAURANTE", "Usuário do tipo dono do estabelecimento");
        tipoUsuarioCriado = new TipoUsuario(1L, "DONO_RESTAURANTE", "Usuário do tipo dono do estabelecimento");
        tipoUsuarioEncontrado = new TipoUsuario(2L, "CLIENTE", "Usuário do tipo cliente");
        tipoUsuarioAtualizado = new TipoUsuario(tipoUsuarioId, "DONO_RESTAURANTE", "Usuário do tipo dono do estabelecimento");
    }

    @Test
    @DisplayName("Deve criar um tipo de usuario e retornar 201 CREATED")
    void deveCriarTipoDeUsuarioRetornandoCreated201() {
        when(tipoUsuarioUseCaseImpl.criarTipoUsuario(criarTipoUsuarioDto)).thenReturn(tipoUsuarioCriado);

        ResponseEntity<RespostaTipoUsuarioDto> response = tipoUsuarioApiController.criarTipoUsuario(criarTipoUsuarioDto);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tipoUsuarioCriado.getNome(), response.getBody().getNome());
        assertEquals(tipoUsuarioCriado.getDescricao(), response.getBody().getDescricao());
        verify(tipoUsuarioUseCaseImpl, times(1)).criarTipoUsuario(criarTipoUsuarioDto);
    }

    @Test
    @DisplayName("Deve listar todos os tipos de usuario e retornar 200 OK")
    void deveListarTodosOsTipoDeUsuarioRetornandoOK201(){
        List<TipoUsuario> tipoUsuarios = Arrays.asList(tipoUsuarioCriado, tipoUsuarioEncontrado);
        when(listarTodosTiposUsuarioUseCaseImpl.listarTipoUsuarios()).thenReturn(tipoUsuarios);

        ResponseEntity<List<RespostaTipoUsuarioDto>> response = tipoUsuarioApiController.listarTiposUsuario();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(tipoUsuarioCriado.getNome(), response.getBody().get(0).getNome());
        assertEquals(tipoUsuarioEncontrado.getNome(), response.getBody().get(1).getNome());

        verify(listarTodosTiposUsuarioUseCaseImpl, times(1)).listarTipoUsuarios();
    }

    @Test
    @DisplayName("Deve  buscar um Tipo De Usuario por id e retornar 200 OK")
    void deveBuscarUmTipoDeUsuarioPorIdRetornandoOK200(){
        when(buscarTipoUsuarioUseCaseImpl.buscarPorId(tipoUsuarioId)).thenReturn(tipoUsuarioEncontrado);

        ResponseEntity<RespostaTipoUsuarioDto> response = tipoUsuarioApiController.buscarPorId(tipoUsuarioId);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tipoUsuarioEncontrado.getNome(), response.getBody().getNome());
        assertEquals(tipoUsuarioEncontrado.getDescricao(), response.getBody().getDescricao());
        verify(buscarTipoUsuarioUseCaseImpl, times(1)).buscarPorId(tipoUsuarioId);

    }
    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum tipo de usuário for encontrado")
    void deveRetornarListaVaziaQuandoNenhumTipoDeUsuarioForEncontradoOK(){
        List<TipoUsuario> tipoUsuarios = List.of();
        when(listarTodosTiposUsuarioUseCaseImpl.listarTipoUsuarios()).thenReturn(tipoUsuarios);
        ResponseEntity<List<RespostaTipoUsuarioDto>> response = tipoUsuarioApiController.listarTiposUsuario();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
    @Test
    @DisplayName("Deve atualizar um tipo de usuário com sucesso e retornar 200 OK")
    void deveAtualizarTipoDeUsuarioRetornandoOk200(){
        when(atualizarTipoUsuarioUseCaseImpl.atualizar(tipoUsuarioId, criarTipoUsuarioDto)).thenReturn(tipoUsuarioAtualizado);

        ResponseEntity<RespostaTipoUsuarioDto> response = tipoUsuarioApiController.atualizar(tipoUsuarioId, criarTipoUsuarioDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        assertEquals(tipoUsuarioId, response.getBody().getId());
        assertEquals("DONO_RESTAURANTE", response.getBody().getNome());

        verify(atualizarTipoUsuarioUseCaseImpl, times(1)).atualizar(tipoUsuarioId, criarTipoUsuarioDto);
    }
    @Test
    @DisplayName("Deve deletar um tipo de usuário com sucesso e retornar 204")
    void deveDeletarTipoDeUsuarioRetornando204(){
        doNothing().when(deletarTipoUsuarioUseCaseImpl).deletar(tipoUsuarioId);

        ResponseEntity<String> response = tipoUsuarioApiController.deletar(tipoUsuarioId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deletarTipoUsuarioUseCaseImpl, times(1)).deletar(tipoUsuarioId);
    }

}