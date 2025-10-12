package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
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
class ListarTodosItensCardapioUseCaseImplTest {
    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private ListarTodosItensCardapioUseCaseImpl listarTodosItensCardapioUseCaseImpl;

    private List<ItemCardapio> listarTodosItensCardapio;

    @BeforeEach
    void setUp() {
        ItemCardapio itemCardapio1 = ItemCardapio.builder().id(2001L).nome("Spaguete").preco(44.9).build();
        ItemCardapio itemCardapio2 = ItemCardapio.builder().id(2002L).nome("Frango Empanado").preco(59.9).build();
        listarTodosItensCardapio = Arrays.asList(itemCardapio1, itemCardapio2);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os itens do cardapio quando encontrados")
    void deveRetornarTodosItensCardapioQuandoEncontrados() {
        when(itemCardapioGateway.buscarTodosItensCardapio()).thenReturn(listarTodosItensCardapio);

        List<ItemCardapio> itensCardapioEncontrados = listarTodosItensCardapioUseCaseImpl.listarTodosItemCardapioUseCase();

        assertNotNull(itensCardapioEncontrados);
        assertFalse(itensCardapioEncontrados.isEmpty());
        assertEquals(listarTodosItensCardapio.size(), itensCardapioEncontrados.size());
        assertEquals("Spaguete", itensCardapioEncontrados.getFirst().getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum item cardapio for encontrado")
    void DeveRetornarListaVaziaQuandoNaoHouverDados() {

        when(itemCardapioGateway.buscarTodosItensCardapio())
                .thenReturn(Collections.emptyList());

        List<ItemCardapio> usuariosEncontrados = listarTodosItensCardapioUseCaseImpl.listarTodosItemCardapioUseCase();

        assertNotNull(usuariosEncontrados);
        assertTrue(usuariosEncontrados.isEmpty());
        assertEquals(0, usuariosEncontrados.size());

        verify(itemCardapioGateway, times(1)).buscarTodosItensCardapio();
    }
}