package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CadastrarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public Usuario criarUsuario(Usuario novoUsuario) {
        return usuarioGateway.criar(novoUsuario);
    }
}