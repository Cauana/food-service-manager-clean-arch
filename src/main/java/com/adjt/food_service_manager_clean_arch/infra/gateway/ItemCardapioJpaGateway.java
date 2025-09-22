package com.adjt.food_service_manager_clean_arch.infra.gateway;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.ItemCardapioRepository;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.RestauranteRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.ItemCardapioEntityMapper;
import com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCardapioJpaGateway implements ItemCardapioGateway {
    private final ItemCardapioRepository repository;
    private final ItemCardapioEntityMapper mapper;
    @Override
    public ItemCardapio criarItemCardapio(ItemCardapio itemCardapio) {
        ItemCardapioEntity entity = mapper.toEntity(itemCardapio);
        repository.save(entity);
        return mapper.toItemCardapio(entity);
    }

    @Override
    public ItemCardapio buscarPorId(Long id) {
        return repository.findById(id)
                         .map(entity -> mapper.toItemCardapio(entity))
                         .orElse(null);
    }

}