package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

public class LoginUsuarioUseCaseImpl implements LoginUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public LoginUsuarioUseCaseImpl(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Usuario usuario = usuarioGateway.buscarPorLogin(request.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(request.getSenha())) {
            return LoginResponseDto.builder()
                    .mensagem("Senha incorreta")
                    .build();
        }

        return LoginResponseDto.builder()
                .mensagem("Login realizado com sucesso!")
                .cpf(usuario.getCpf())
                .nome(usuario.getNome())
                .tipoUsuario(usuario.getTipoUsuario())
                .restaurantesIds(usuario.getRestaurantes().stream()
                   .map(Restaurante::getId)
                   .toList())
                .build();
    }
}

