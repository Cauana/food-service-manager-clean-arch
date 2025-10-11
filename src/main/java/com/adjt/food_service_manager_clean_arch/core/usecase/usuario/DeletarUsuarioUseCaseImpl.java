package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeletarUsuarioUseCaseImpl {

    private final UsuarioGateway usuarioGateway;

    public void deletarUsuario(Long id){
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado."
                ));
        usuarioGateway.deletar(usuario);
    }
}
