package com.adjt.food_service_manager_clean_arch.core.usecase.login;

import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;

public interface LoginUsuarioUseCase {
    LoginResponseDto login(LoginRequestDto request);
}
