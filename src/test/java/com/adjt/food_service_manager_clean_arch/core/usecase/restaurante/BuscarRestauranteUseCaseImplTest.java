package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private BuscarRestauranteUseCaseImpl buscarRestauranteUseCaseImpl;

    private Restaurante restauranteMock;
    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder().id(201L).nome("Jose Carlos").build();
        restauranteMock = Restaurante.builder()
                .id(1001L)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(usuarioMock)
                .build();
    }

    @Test
    @DisplayName("Deve buscar e retornar o restaurante encontrado após uma busca por id")
    void deveBuscarRestaurantePorId() {
        Long id = 1001L;

        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.of(restauranteMock));

        Restaurante restauranteEncontrado = buscarRestauranteUseCaseImpl.buscarRestaurante(id);

        assertNotNull(restauranteEncontrado);
        assertEquals(id, restauranteEncontrado.getId());
        assertEquals(restauranteMock.getNome(), restauranteEncontrado.getNome());

        verify(restauranteGateway, times(1)).buscarPorId(id);
    }


    @Test
    @DisplayName("Deve tentar buscar um restaurante não cadastrado e lançar uma exceção quando ele não for encontrado.")
    void deveLancarExcecaoQuandoRestauranteNaoForEncontrado() {
        Long id = 1002L;
        when(restauranteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,() -> buscarRestauranteUseCaseImpl.buscarRestaurante(id));

        assertEquals("Restaurante Não Encontrado", exception.getMessage());

        verify(restauranteGateway, times(1)).buscarPorId(id);
    }


}