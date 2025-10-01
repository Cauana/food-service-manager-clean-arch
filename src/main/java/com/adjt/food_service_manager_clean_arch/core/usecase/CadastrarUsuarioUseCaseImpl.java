package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CadastrarUsuarioUseCaseImpl {
    
    private final UsuarioGateway usuarioGateway;

    public Usuario criarUsuario(CriarUsuarioDto novoUsuario) {

        return usuarioGateway.criar(map(novoUsuario));
    }

    public Usuario map(CriarUsuarioDto dto) {

		return Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.cpf(dto.getCpf())
				.login(dto.getLogin())
				.senha(dto.getSenha())
				.tipoUsuario(dto.getTipoUsuario())
				.build();
	}
}
