package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import java.util.Collections;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import com.adjt.food_service_manager_clean_arch.core.validation.CpfValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class CadastrarUsuarioUseCaseImpl {
    
    private final UsuarioGateway usuarioGateway;

    public Usuario criarUsuario(CriarUsuarioDto novoUsuario) {

		//validação do cpf
		if(!CpfValidator.isValid(novoUsuario.getCpf())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CPF inválido");
		}

		//verificação se já existe
		if(usuarioGateway.buscarPorCpf(novoUsuario.getCpf()).isPresent()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Já existe usuário com este CPF");
		}
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
				.restaurantes(Collections.emptyList())
				.build();
	}
}
