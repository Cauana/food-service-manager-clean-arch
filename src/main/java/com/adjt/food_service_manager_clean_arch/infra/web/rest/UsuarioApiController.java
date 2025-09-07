package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarUsuarioUseCaseImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioApiController {

    private final CadastrarUsuarioUseCaseImpl usuarioController;

    @PostMapping
    public Usuario criarUsuario(@RequestBody CriarUsuarioDto usuarioDto) {
        Usuario usuario = usuarioController.criarUsuario(usuarioDto);
        log.info("Usuário criado com ID: {}", usuario.getId());
        return usuario;
    }

    public CriarUsuarioDto map(Usuario usuario) {

		return CriarUsuarioDto.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.cpf(usuario.getCpf())
				.login(usuario.getLogin())
				.senha(usuario.getSenha())
				.tipoUsuario(usuario.getTipoUsuario())
				.build();
	}
}
