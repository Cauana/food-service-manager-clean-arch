package com.adjt.food_service_manager_clean_arch.infra.mapper;

import org.springframework.stereotype.Component;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestauranteEntityMapper {

    private final UsuarioEntityMapper usuarioEntityMapper;


    public RestauranteEntity toEntity(Restaurante restaurante) {
        if(restaurante == null) return null;
        return RestauranteEntity.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .endereco(restaurante.getEndereco())
                .tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
                .donoRestaurante(usuarioEntityMapper.toEntity(restaurante.getDonoRestaurante()))
                .build();
    }

    public Restaurante toRestaurante(RestauranteEntity entity) {
        if(entity == null) return null;
        return Restaurante.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .endereco(entity.getEndereco())
                .tipoCozinha(entity.getTipoCozinha())
                .donoRestaurante(usuarioEntityMapper.toUsuario(entity.getDonoRestaurante()))
                .build();
    }

}
