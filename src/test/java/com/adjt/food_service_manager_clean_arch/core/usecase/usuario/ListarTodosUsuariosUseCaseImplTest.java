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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarTodosUsuariosUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ListarTodosUsuariosUseCaseImpl listarTodosUsuariosUseCaseImpl;

    private List<Usuario> listaUsuarios;

    @BeforeEach
    void setUp() {
        Usuario usuario1 = Usuario.builder().id(2001L).nome("Joao Pedro").cpf("01203405678").build();
        Usuario usuario2 = Usuario.builder().id(2002L).nome("Maria Alice").cpf("00100200344").build();
        listaUsuarios = Arrays.asList(usuario1, usuario2);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os usuários quando encontrados")
    void deveRetornarTodosUsuariosQuandoEncontrados() {
        when(usuarioGateway.buscarTodosUsuarios()).thenReturn(listaUsuarios);

        List<Usuario> usuariosEncontrados = listarTodosUsuariosUseCaseImpl.buscarTodosUsuarios();

        assertNotNull(usuariosEncontrados);
        assertFalse(usuariosEncontrados.isEmpty());
        assertEquals(listaUsuarios.size(), usuariosEncontrados.size());
        assertEquals("Joao Pedro", usuariosEncontrados.getFirst().getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum usuário é encontrado")
    void listarTodosUsuarios_DeveRetornarListaVazia_QuandoNaoHouverDados() {

        when(usuarioGateway.buscarTodosUsuarios())
                .thenReturn(Collections.emptyList());

        List<Usuario> usuariosEncontrados = listarTodosUsuariosUseCaseImpl.buscarTodosUsuarios();

        assertNotNull(usuariosEncontrados);
        assertTrue(usuariosEncontrados.isEmpty());
        assertEquals(0, usuariosEncontrados.size());

        verify(usuarioGateway, times(1)).buscarTodosUsuarios();
    }
}