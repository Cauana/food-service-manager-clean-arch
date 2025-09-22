package com.adjt.food_service_manager_clean_arch.core.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;

public interface ItemCardapioGateway {
    ItemCardapio criarItemCardapio(ItemCardapio item);
    ItemCardapio buscarPorId(Long id);
}
