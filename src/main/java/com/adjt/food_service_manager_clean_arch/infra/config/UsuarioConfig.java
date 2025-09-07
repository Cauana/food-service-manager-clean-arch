package com.adjt.food_service_manager_clean_arch.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarUsuarioUseCaseImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class UsuarioConfig {

    @Bean
    CadastrarUsuarioUseCaseImpl usuarioGateway(UsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioUseCaseImpl(usuarioGateway);
    }
}
