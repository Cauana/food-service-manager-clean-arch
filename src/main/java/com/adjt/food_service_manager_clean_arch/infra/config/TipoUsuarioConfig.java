package com.adjt.food_service_manager_clean_arch.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.TipoUsuarioUseCaseImpl;

@Configuration
public class TipoUsuarioConfig {

    @Bean
    public TipoUsuarioUseCaseImpl cadastraTipoUsuarioUseCase(TipoUsuarioGateway tipoUsuarioGateway) {
        return new TipoUsuarioUseCaseImpl(tipoUsuarioGateway);
    }
}
