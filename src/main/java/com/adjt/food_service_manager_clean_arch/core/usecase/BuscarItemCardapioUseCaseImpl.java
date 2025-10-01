package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarItemCardapioUseCaseImpl {

    private final ItemCardapioGateway itemCardapioGateway;

    public ItemCardapio buscarItemCardapio(Long id) {
        return itemCardapioGateway.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Item do Cardápio Não Encontrado!"));
    }
}
