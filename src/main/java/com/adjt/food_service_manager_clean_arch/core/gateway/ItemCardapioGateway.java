package com.adjt.food_service_manager_clean_arch.core.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;

public interface ItemCardapioGateway {
    ItemCardapio criarItemCardapio(ItemCardapio item);
    Optional<ItemCardapio> buscarPorId(Long id);
    List<ItemCardapio> buscarTodosItensCardapio();
}
