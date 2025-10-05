package com.adjt.food_service_manager_clean_arch.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.login.LoginUsuarioUseCase;
import com.adjt.food_service_manager_clean_arch.core.usecase.login.LoginUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.web.rest.LoginApiController;

@Configuration
public class LoginConfig {

    @Bean
    public LoginUsuarioUseCaseImpl loginUsuarioUseCase(UsuarioGateway loginGateway) {
        return new LoginUsuarioUseCaseImpl(loginGateway);
    }

    @Bean
    public LoginUsuarioUseCase loginUsuarioUseCaseInterface(LoginUsuarioUseCaseImpl loginUsuarioUseCaseImpl) {
        return loginUsuarioUseCaseImpl;
    }

    @Bean
    public LoginApiController loginApiController(LoginUsuarioUseCase loginUsuarioUseCase) {
        return new LoginApiController(loginUsuarioUseCase);
    }
    
}