package com.adjt.food_service_manager_clean_arch.infra.mapper;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
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
                .restaurantes(
                    usuarioEntity.getRestaurantes() != null
                        ? usuarioEntity.getRestaurantes().stream()
                            .map(restauranteEntity -> Restaurante.builder()
                                .id(restauranteEntity.getId())
                                .nome(restauranteEntity.getNome())
                                .endereco(restauranteEntity.getEndereco())
                                .tipoCozinha(restauranteEntity.getTipoCozinha())
                                .horarioFuncionamento(restauranteEntity.getHorarioFuncionamento())
                                .build())
                            .toList()
                        : Collections.emptyList()
                )
                .build();
    }
}
