package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class BuscarUsuarioUseCaseImpl {

    private final UsuarioGateway usuarioGateway;

    public Usuario buscarUsuario(Long id) {
        if(id == null || id <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ID de usuário inválido");
        }
        return  usuarioGateway.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário Não Encontrado"));
    }

}
