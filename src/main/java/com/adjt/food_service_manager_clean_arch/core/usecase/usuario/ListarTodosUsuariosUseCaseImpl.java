package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListarTodosUsuariosUseCaseImpl {

    private final UsuarioGateway usuarioGateway;

    public List<Usuario> listarTodosUsuarios(){
        return usuarioGateway.buscarTodosUsuarios();
    }
}
