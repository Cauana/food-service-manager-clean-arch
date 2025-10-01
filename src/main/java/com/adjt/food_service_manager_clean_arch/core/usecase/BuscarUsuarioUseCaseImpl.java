package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarUsuarioUseCaseImpl {

    private final UsuarioGateway usuarioGateway;

    public Usuario buscarUsuario(Long id) {
        return  usuarioGateway.buscarPorId(id).orElseThrow(() -> new ResourceNotFoundException("Usuário Não Encontrado"));
    }

}
