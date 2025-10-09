package com.adjt.food_service_manager_clean_arch.core.gateway;

import java.util.List;
import java.util.Optional;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;

public interface UsuarioGateway {
    Usuario criar(Usuario usuario);
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorLogin(String login);
    List<Usuario> buscarTodosUsuarios();
    Optional<Usuario> buscarPorCpf(String cpf);
    void deletar(Usuario usuario);
}