package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.usuario.BuscarUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.usuario.CadastrarUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.usuario.ListarTodosUsuariosUseCaseImpl;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApiControllerTest {

    @Mock
    private CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCaseImpl;

    @Mock
    private BuscarUsuarioUseCaseImpl buscarUsuarioUseCaseImpl;

    @Mock
    private ListarTodosUsuariosUseCaseImpl listarTodosUsuariosUseCaseImpl;

    @InjectMocks
    private UsuarioApiController usuarioApiController;

    private CriarUsuarioDto criarUsuarioDto;
    private Usuario usuarioCriado;
    private Usuario usuarioEncontrado;

    @BeforeEach
    void setUp() {
        criarUsuarioDto = new CriarUsuarioDto(
                "João da Silva",
                "joao.silva@teste.com",
                "12345678900",
                "joao.login",
                "senha123",
                "CLIENTE"
        );

        usuarioCriado = new Usuario(
                1L,
                "João da Silva",
                "joao.silva@teste.com",
                "12345678900",
                "joao.login",
                "senha123",
                "CLIENTE",
                Collections.emptyList()
        );

        usuarioEncontrado = new Usuario(
                2L,
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
    @DisplayName("Deve criar um usuário com sucesso e retornar 201 CREATED")
    void deveCriarUsuarioRetornarCreatedQuandoSucesso() {
        when(cadastrarUsuarioUseCaseImpl.criarUsuario(criarUsuarioDto)).thenReturn(usuarioCriado);

        ResponseEntity<RespostaUsuarioDto> response = usuarioApiController.criarUsuario(criarUsuarioDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioCriado.getId(), response.getBody().getId());
        assertEquals(usuarioCriado.getNome(), response.getBody().getNome());
        verify(cadastrarUsuarioUseCaseImpl, times(1)).criarUsuario(criarUsuarioDto);
    }

    @Test
    @DisplayName("Deve buscar um usuário por ID com sucesso e retornar 200 OK")
    void deveBuscarUsuarioPorIdRetornarOkQuandoUsuarioEncontrado() {
        Long usuarioId = 2L;

        when(buscarUsuarioUseCaseImpl.buscarUsuario(usuarioId)).thenReturn(usuarioEncontrado);
        ResponseEntity<RespostaUsuarioDto> response = usuarioApiController.buscarUsuarioPorId(usuarioId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuarioEncontrado.getId(), response.getBody().getId());
        assertEquals(usuarioEncontrado.getNome(), response.getBody().getNome());
        verify(buscarUsuarioUseCaseImpl, times(1)).buscarUsuario(usuarioId);

    }

    @Test
    @DisplayName("Deve listar todos os usuários e retornar 200 OK")
    void deveListarTodosUsuariosRetornarOkComListaDeUsuarios() {
        List<Usuario> usuarios = Arrays.asList(usuarioCriado, usuarioEncontrado);
        when(listarTodosUsuariosUseCaseImpl.listarTodosUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<RespostaUsuarioDto>> response = usuarioApiController.listarUsuarios();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(usuarioCriado.getNome(), response.getBody().get(0).getNome());
        assertEquals(usuarioEncontrado.getNome(), response.getBody().get(1).getNome());

        verify(listarTodosUsuariosUseCaseImpl, times(1)).listarTodosUsuarios();
    }

    @Test
    @DisplayName("Deve restornar uma lista vazia quando nenhum usuário for encontrado")
    void deveRetornarListaVaziaQuandoNenhumUsuarioForEncontrado() {
        List<Usuario> usuarios = List.of();
        when(listarTodosUsuariosUseCaseImpl.listarTodosUsuarios()).thenReturn(usuarios);
        ResponseEntity<List<RespostaUsuarioDto>> response = usuarioApiController.listarUsuarios();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
}
