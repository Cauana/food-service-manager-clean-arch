package com.adjt.food_service_manager_clean_arch.infra.mapper;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;

public class UsuarioEntityMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        if(usuario == null) return null;
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .cpf(usuario.getCpf())
                .login(usuario.getLogin())
                .senha(usuario.getSenha())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
    }

    public Usuario toUsuario(UsuarioEntity usuarioEntity) {
        if(usuarioEntity == null) return null;
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .cpf(usuarioEntity.getCpf())
                .login(usuarioEntity.getLogin())
                .senha(usuarioEntity.getSenha())
                .tipoUsuario(usuarioEntity.getTipoUsuario())
                .build();
    }
}
