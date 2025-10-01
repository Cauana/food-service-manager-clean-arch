package com.adjt.food_service_manager_clean_arch.infra.gateway;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.ItemCardapioRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.ItemCardapioEntityMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<ItemCardapio> buscarPorId(Long id) {
        return repository.findById(id)
                         .map(entity -> mapper.toItemCardapio(entity));
    }

    @Override
    public List<ItemCardapio> buscarTodosItensCardapio() {
       List<ItemCardapioEntity> itemCardapioEntities =repository.findAll();
       return itemCardapioEntities.stream().map(mapper::toItemCardapio).collect(Collectors.toList());
    }

}