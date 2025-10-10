package com.adjt.food_service_manager_clean_arch.infra.mapper;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TipoUsuarioEntityMapper {

    public TipoUsuarioEntity toEntity(TipoUsuario tipoUsuario) {
        if(tipoUsuario == null) return null;
        return TipoUsuarioEntity.builder()
                .id(tipoUsuario.getId())
                .nome(tipoUsuario.getNome())
                .descricao(tipoUsuario.getDescricao())
                .build();
    }

    public TipoUsuario toTipoUsuario(TipoUsuarioEntity entity) {
        if(entity == null) return null;
        return TipoUsuario.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .build();
    }

    public TipoUsuario toDomain(TipoUsuarioEntity entity) {
        if(entity == null){
            return null;
        }

        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(entity.getId());
        tipoUsuario.setNome(entity.getNome());
        tipoUsuario.setDescricao(entity.getDescricao());
        return tipoUsuario;
    }
}