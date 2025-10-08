package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaTipoUsuarioDto;

public interface TipoUsuarioUseCase {

    RespostaTipoUsuarioDto criarTipoUsuario(CriarTipoUsuarioDto request);

}
