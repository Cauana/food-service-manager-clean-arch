package com.adjt.food_service_manager_clean_arch.infra.mapper;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCardapioEntityMapper {
    private final RestauranteEntityMapper restauranteEntityMapper;


    public ItemCardapioEntity toEntity(ItemCardapio itemCardapio) {
        return ItemCardapioEntity.builder()
                .id(itemCardapio.getId())
                .nome(itemCardapio.getNome())
                .descricao(itemCardapio.getDescricao())
                .preco(itemCardapio.getPreco())
                .restaurante(restauranteEntityMapper.toEntity(itemCardapio.getRestaurante()))
                .build();
    }

    public ItemCardapio toItemCardapio(ItemCardapioEntity entity) {
        return ItemCardapio.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .preco(entity.getPreco())
                .restaurante(restauranteEntityMapper.toRestaurante(entity.getRestaurante()))
                .build();
    }
}
