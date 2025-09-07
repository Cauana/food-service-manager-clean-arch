package com.adjt.food_service_manager_clean_arch.infra.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.RestauranteRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

    private final RestauranteRepository repository;
    private final RestauranteEntityMapper mapper;

    @Override
    public Restaurante criarRestaurante(Restaurante restaurante) {
        RestauranteEntity entity = mapper.toEntity(restaurante);
        repository.save(entity);
        return mapper.toRestaurante(entity, null); // se precisar do mapper de usuário, injete
    }

    @Override
    public Restaurante buscarPorId(Long id) {
        return repository.findById(id)
                         .map(entity -> mapper.toRestaurante(entity, null))
                         .orElse(null);
    }


}
