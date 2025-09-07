package com.adjt.food_service_manager_clean_arch.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.RestauranteRepository;
import com.adjt.food_service_manager_clean_arch.infra.gateway.RestauranteJpaGateway;

@Configuration
public class RestauranteConfig {

    @Bean
    public CadastrarRestauranteUseCaseImpl cadastrarRestauranteUseCase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
        return new CadastrarRestauranteUseCaseImpl(restauranteGateway, usuarioGateway);
    }

    @Bean
    public RestauranteGateway restauranteGateway(RestauranteRepository restauranteRepository) {
        return new RestauranteJpaGateway(restauranteRepository, new com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper());
    }
}
