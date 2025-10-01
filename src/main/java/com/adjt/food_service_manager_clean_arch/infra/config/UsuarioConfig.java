package com.adjt.food_service_manager_clean_arch.infra.config;

import com.adjt.food_service_manager_clean_arch.core.usecase.BuscarUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.ListarTodosUsuariosUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.UsuarioRepository;
import com.adjt.food_service_manager_clean_arch.infra.gateway.UsuarioJpaGateway;


@Configuration
public class UsuarioConfig {

    @Bean
    public CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    public BuscarUsuarioUseCaseImpl buscarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        return new BuscarUsuarioUseCaseImpl(usuarioGateway);
    }

    @Bean
    public ListarTodosUsuariosUseCaseImpl listarTodosUsuariosUseCase(UsuarioGateway usuarioGateway) {
        return new ListarTodosUsuariosUseCaseImpl(usuarioGateway);
    }

    @Bean
    public UsuarioGateway usuarioGateway(UsuarioRepository usuarioRepository, UsuarioEntityMapper usuarioEntityMapper) {
        return new UsuarioJpaGateway(usuarioRepository, usuarioEntityMapper);
    }
}
