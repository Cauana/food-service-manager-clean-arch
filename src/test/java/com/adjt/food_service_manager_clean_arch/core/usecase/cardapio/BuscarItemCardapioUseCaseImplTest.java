package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarItemCardapioUseCaseImplTest {
    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private BuscarItemCardapioUseCaseImpl buscarItemCardapioUseCaseImpl;

    private ItemCardapio itemCardapioMock;

    @BeforeEach
    void setUp() {
        itemCardapioMock = ItemCardapio.builder()
                .id(1001L)
                .nome("Iscas de Frango")
                .descricao("Pedaços de peito de frango com molho especial")
                .preco(34.9)
                .disponibilidade(true)
                .foto("/imagens/cardapio/itens/iscas-frango.png")
                .restaurante(Restaurante.builder().id(101L).build())
                .build();
    }

    ;


    @Test
    @DisplayName("Deve buscar e retornar item do cardápio encontrado após uma busca por id")
    void deveBuscarItemCardapioPorId() {
        Long id = 1001L;

        when(itemCardapioGateway.buscarPorId(id)).thenReturn(Optional.of(itemCardapioMock));

        ItemCardapio response = buscarItemCardapioUseCaseImpl.buscarItemCardapio(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals(itemCardapioMock.getNome(), response.getNome());

        verify(itemCardapioGateway, times(1)).buscarPorId(id);
    }


    @Test
    @DisplayName("Deve tentar buscar um item do cardápio não cadastrado e lançar uma exceção quando ele não for encontrado.")
    void deveLancarExcecaoQuandoItemCardapioNaoForEncontrado() {
        Long id = 10002L;
        when(itemCardapioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> buscarItemCardapioUseCaseImpl.buscarItemCardapio(id));

        assertEquals("Item do Cardápio Não Encontrado!", exception.getMessage());

        verify(itemCardapioGateway, times(1)).buscarPorId(id);
    }
}