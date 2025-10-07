package com.adjt.food_service_manager_clean_arch.infra.config;

import com.adjt.food_service_manager_clean_arch.core.usecase.BuscarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.ListarTodosRestaurantesUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.RestauranteRepository;
import com.adjt.food_service_manager_clean_arch.infra.gateway.RestauranteJpaGateway;
import com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper;

@Configuration
public class RestauranteConfig {

    @Bean
    public CadastrarRestauranteUseCaseImpl cadastrarRestauranteUseCase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {
        return new CadastrarRestauranteUseCaseImpl(restauranteGateway, usuarioGateway);
    }

    @Bean
    public BuscarRestauranteUseCaseImpl buscarRestauranteUseCase(RestauranteGateway restauranteGateway  ) {
        return new BuscarRestauranteUseCaseImpl(restauranteGateway);
    }

    @Bean
    public ListarTodosRestaurantesUseCaseImpl listarTodosRestaurantesUseCase(RestauranteGateway restauranteGateway) {
        return new ListarTodosRestaurantesUseCaseImpl(restauranteGateway);
    }

    @Bean
    public RestauranteGateway restauranteGateway(RestauranteRepository restauranteRepository, RestauranteEntityMapper restauranteEntityMapper) {
        return new RestauranteJpaGateway(restauranteRepository, restauranteEntityMapper);
    }
}
