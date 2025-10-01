package com.adjt.food_service_manager_clean_arch.infra.config;

import com.adjt.food_service_manager_clean_arch.core.usecase.BuscarItemCardapioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.ListarTodosItensCardapioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.mapper.ItemCardapioEntityMapper;
import com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper;
import com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarItemCardapioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.ItemCardapioRepository;
import com.adjt.food_service_manager_clean_arch.infra.gateway.ItemCardapioJpaGateway;
import com.adjt.food_service_manager_clean_arch.infra.web.rest.ItemCardapioApiController;


@Configuration
public class ItemConfig {
    
    @Bean
    public CadastrarItemCardapioUseCaseImpl cadastrarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway, RestauranteGateway restauranteGateway) {
        return new CadastrarItemCardapioUseCaseImpl(itemCardapioGateway, restauranteGateway);
    }
    @Bean
    public BuscarItemCardapioUseCaseImpl buscarItemCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new BuscarItemCardapioUseCaseImpl(itemCardapioGateway);
    }
    @Bean
    public ListarTodosItensCardapioUseCaseImpl listarTodosItensCardapioUseCase(ItemCardapioGateway itemCardapioGateway) {
        return new ListarTodosItensCardapioUseCaseImpl(itemCardapioGateway);
    }

    @Bean
    public ItemCardapioGateway itemCardapioGateway(ItemCardapioRepository itemCardapioRepository, ItemCardapioEntityMapper itemCardapioEntityMapper) {
        return new ItemCardapioJpaGateway(itemCardapioRepository, itemCardapioEntityMapper);
    }

    @Bean
    public RestauranteEntityMapper restauranteEntityMapper(UsuarioEntityMapper usuarioEntityMapper) {
        return new RestauranteEntityMapper(usuarioEntityMapper);
    }

}
