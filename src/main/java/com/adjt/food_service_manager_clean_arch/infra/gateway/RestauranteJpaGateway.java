package com.adjt.food_service_manager_clean_arch.infra.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return mapper.toRestaurante(entity);
    }

	@Override
	public Optional<Restaurante> buscarPorId(Long id) {
		Optional<RestauranteEntity> restauranteEntityOp = repository.findById(id);
		if (restauranteEntityOp.isPresent()) {
			var restauranteEntity = restauranteEntityOp.get();
			var restaurante = mapper.toRestaurante(restauranteEntity);
			return Optional.of(restaurante);
		}
		return Optional.empty();
	}

	@Override
	public List<Restaurante> buscarTodosRestaurantes() {
		List<RestauranteEntity> entities = repository.findAll();
		return entities.stream().map(mapper::toRestaurante).collect(Collectors.toList());
	}

}
