package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.usuario.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioApiController {

    private final CadastrarUsuarioUseCaseImpl usuarioController;
	private final BuscarUsuarioUseCaseImpl buscarUsuarioController;
	private final ListarTodosUsuariosUseCaseImpl listarTodosUsuariosUseCase;
    private final AtualizarUsuarioUseCaseImpl atualizarUsuarioUseCase;
    private final DeletarUsuarioUseCaseImpl deletarUsuarioUseCase;

    @PostMapping
    public ResponseEntity<RespostaUsuarioDto> criarUsuario(@RequestBody CriarUsuarioDto usuarioDto) {
        Usuario usuario = usuarioController.criarUsuario(usuarioDto);
        log.info("Usuário criado com ID: {}", usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(map(usuario));
    }

	@GetMapping
	public ResponseEntity<List<RespostaUsuarioDto>> buscarUsuarioPorId() {
		List<Usuario> usuarios = listarTodosUsuariosUseCase.buscarTodosUsuarios();
		log.info("Listando todos os Usuarios");
		List<RespostaUsuarioDto> respostaUsuarioDtos = usuarios.stream().map(this::map).toList();
		return ResponseEntity.ok(respostaUsuarioDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RespostaUsuarioDto> buscarUsuarioPorId(@PathVariable Long id) {
		Usuario usuario = buscarUsuarioController.buscarUsuario(id);
		log.info("Usuario buscado com ID: {}", id);
		return ResponseEntity.ok(map(usuario));
	}

    @PutMapping("/{id}")
    public ResponseEntity<RespostaUsuarioDto> atualizar(
            @PathVariable Long id,
            @RequestBody CriarUsuarioDto usuarioDto
    ){

        Usuario usuarioAtualizado = atualizarUsuarioUseCase.atualizarUsuario(id,usuarioDto);
        log.info("Usuário atualizado: {}, id: {}", id);
        return ResponseEntity.ok(map(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id){
        deletarUsuarioUseCase.deletarUsuario(id);
        log.info("Usuário deletado com ID: {}", id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    public RespostaUsuarioDto map(Usuario usuario) {
		if(usuario == null) return null;
		return RespostaUsuarioDto.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.cpf(usuario.getCpf())
				.login(usuario.getLogin())
				.senha(usuario.getSenha())
				.tipoUsuario(usuario.getTipoUsuario())
				.build();
	}
}
