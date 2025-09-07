package com.adjt.food_service_manager_clean_arch.infra.mapper;

import org.springframework.stereotype.Component;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;

@Component
public class RestauranteEntityMapper {

    public RestauranteEntity toEntity(Restaurante restaurante) {
        return RestauranteEntity.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(restaurante.getEndereco())
                .tipoCozinha(restaurante.getTipoCozinha())
                .idDonoRestaurante(restaurante.getDonoRestaurante())
                .build();
    }

    public Restaurante toRestaurante(RestauranteEntity entity, UsuarioEntityMapper usuarioMapper) {
        return Restaurante.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .endereco(entity.getEndereco())
                .tipoCozinha(entity.getTipoCozinha())
                .donoRestaurante(entity.getIdDonoRestaurante())
                .build();
    }

}
