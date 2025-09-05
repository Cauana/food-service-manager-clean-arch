package com.adjt.food_service_manager_clean_arch.core.gateway;

import java.util.Optional;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;

public interface UsuarioGateway {
    Usuario criar(Usuario usuario);
    Optional<Usuario> buscarPorCpf(String cpf);

}