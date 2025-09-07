package com.adjt.food_service_manager_clean_arch.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.database.UsuarioJpaGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.UsuarioRepository;


@Configuration
public class UsuarioConfig {

    @Bean
    public CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    public UsuarioGateway usuarioGateway(UsuarioRepository usuarioRepository) {
        return new UsuarioJpaGateway(usuarioRepository, new com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper());
    }
}
