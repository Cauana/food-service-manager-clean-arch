package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListarTodosRestaurantesUseCaseImpl {

    private final RestauranteGateway restauranteGateway;

    public List<Restaurante> buscarTodosRestaurantes(){
        return restauranteGateway.buscarTodosRestaurantes();
    }
}
