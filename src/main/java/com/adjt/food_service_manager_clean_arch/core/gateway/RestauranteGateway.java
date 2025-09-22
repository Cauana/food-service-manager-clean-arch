package com.adjt.food_service_manager_clean_arch.core.gateway;

import java.util.Optional;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;

public interface RestauranteGateway {
    Restaurante criarRestaurante(Restaurante restaurante);
    Optional<Restaurante> buscarPorId(Long id);
}
