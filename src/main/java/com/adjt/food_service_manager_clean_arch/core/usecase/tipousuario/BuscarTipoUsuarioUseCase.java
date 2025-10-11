package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;

public interface BuscarTipoUsuarioUseCase {
    TipoUsuario buscarPorId(Long id);
}
