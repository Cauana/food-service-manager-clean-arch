package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;

public interface AtualizarTipoUsuarioUseCase {
    TipoUsuario atualizar(Long id, CriarTipoUsuarioDto tipoUsuario);
}
