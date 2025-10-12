package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListarTodosItensCardapioUseCaseImpl {

    private final ItemCardapioGateway itemCardapioGateway;

    public List<ItemCardapio> listarTodosItemCardapioUseCase(){
       return itemCardapioGateway.buscarTodosItensCardapio();
    }
}
