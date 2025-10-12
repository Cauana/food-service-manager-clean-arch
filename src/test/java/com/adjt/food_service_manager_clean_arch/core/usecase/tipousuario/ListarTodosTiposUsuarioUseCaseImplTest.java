package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
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
class ListarTodosTiposUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private ListarTodosTiposUsuarioUseCaseImpl listarTodosTiposUsuarioUseCaseImpl;

    private List<TipoUsuario> listarTiposUsuario;

    @BeforeEach
    void setUp() {
        TipoUsuario tipoUsuario1 = TipoUsuario.builder().id(2001L).nome("CLIENTE").descricao("Usuario Cliente").build();
        TipoUsuario tipoUsuario2 = TipoUsuario.builder().id(2002L).nome("DONO_RESTAURANTE").descricao("Usuario Dono").build();
        listarTiposUsuario = Arrays.asList(tipoUsuario1, tipoUsuario2);
    }
 //TODO: Descomentar e corrigir esse trecho

 /*   @Test
    @DisplayName("Deve retornar uma lista com todos os tipos de usuários quando encontrados")
    void deveRetornarTodosTiposUsuariosQuandoEncontrados() {
        when(tipoUsuarioGateway.buscarTodosTiposUsuario()).thenReturn(listarTiposUsuario);

        List<TipoUsuario> response = listarTodosTiposUsuarioUseCaseImpl.listarTipoUsuarios();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(listarTiposUsuario.size(), response.size());
        assertEquals("CLIENTE", response.getFirst().getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum tipo de usuário é encontrado")
    void DeveRetornarListaVaziaQuandoNaoHouverDados() {

        when(tipoUsuarioGateway.buscarTodosTiposUsuario())
                .thenReturn(Collections.emptyList());

        List<TipoUsuario> response = listarTodosTiposUsuarioUseCaseImpl.listarTipoUsuarios();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(tipoUsuarioGateway, times(1)).buscarTodosTiposUsuario();
    }*/
}