package com.adjt.food_service_manager_clean_arch.core.gateway;

import java.util.List;
import java.util.Optional;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;

public interface TipoUsuarioGateway {
    TipoUsuario criarTipoUsuario(TipoUsuario tipoUsuario);
    Optional<TipoUsuario> buscarPorId(Long id);
    List<TipoUsuario> buscarTodosTiposUsuario();
}
