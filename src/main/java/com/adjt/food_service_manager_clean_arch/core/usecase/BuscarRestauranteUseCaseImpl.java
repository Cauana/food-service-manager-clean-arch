package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;

    public Restaurante buscarRestaurante(Long restauranteId) {
        return restauranteGateway.buscarPorId(restauranteId).orElseThrow(() -> new ResourceNotFoundException("Restaurante Não Encontrado"));
    }


}
