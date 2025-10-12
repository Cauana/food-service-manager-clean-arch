package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
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
class ListarTodosRestaurantesUseCaseImplTest {
    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private ListarTodosRestaurantesUseCaseImpl listarTodosRestaurantesUseCaseImpl;

    private List<Restaurante> listaRestaurantes;

    @BeforeEach
    void setUp() {
        Restaurante restaurante1 = Restaurante.builder().id(2001L).nome("Lucky").build();
        Restaurante restaurante2 = Restaurante.builder().id(2002L).nome("Saigon").build();
        listaRestaurantes = Arrays.asList(restaurante1, restaurante2);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os restaurantes encontrados")
    void deveRetornarTodosRestaurantesEncontrados() {
        when(restauranteGateway.buscarTodosRestaurantes()).thenReturn(listaRestaurantes);

        List<Restaurante> restaurantesEncontrados = listarTodosRestaurantesUseCaseImpl.listarTodosRestaurantes();

        assertNotNull(restaurantesEncontrados);
        assertFalse(restaurantesEncontrados.isEmpty());
        assertEquals(listaRestaurantes.size(), restaurantesEncontrados.size());
        assertEquals("Lucky", restaurantesEncontrados.getFirst().getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nenhum restaurante for encontrado")
    void deveRetornarListaVaziaquandoNaoHouverRestaurantes() {

        when(restauranteGateway.buscarTodosRestaurantes())
                .thenReturn(Collections.emptyList());

        List<Restaurante> restaurantesEncontrados = listarTodosRestaurantesUseCaseImpl.listarTodosRestaurantes();

        assertNotNull(restaurantesEncontrados);
        assertTrue(restaurantesEncontrados.isEmpty());
        assertEquals(0, restaurantesEncontrados.size());

        verify(restauranteGateway, times(1)).buscarTodosRestaurantes();
    }
}