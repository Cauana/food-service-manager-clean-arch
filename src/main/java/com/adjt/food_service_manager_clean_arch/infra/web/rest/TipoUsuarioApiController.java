package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.BuscarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.ListarTodosRestaurantesUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioApiController {

    private final TipoUsuarioUseCaseImpl cadastrarTipoUsuarioController;
    private final ListarTodosTiposUsuarioUseCaseImpl listarTodosTiposUsuarioController;
    private final BuscarTipoUsuarioUseCaseImpl buscarTipoUsuarioController;
    private final AtualizarTipoUsuarioUseCaseImpl atualizarTipoUsuarioController;
    private final DeletarTipoUsuarioUseCaseImpl deletarTipoUsuarioUseCase;

    @PostMapping
    public ResponseEntity<RespostaTipoUsuarioDto> criarTipoUsuario(@RequestBody CriarTipoUsuarioDto tipoUsuarioDto) {
        TipoUsuario tipoUsuario = cadastrarTipoUsuarioController.criarTipoUsuario(tipoUsuarioDto);
        RespostaTipoUsuarioDto respostaTipoUsuarioDto = map(tipoUsuario);
        log.info("Tipo de Usuário criado com ID: {}, nome: {}", respostaTipoUsuarioDto.getId(), respostaTipoUsuarioDto.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaTipoUsuarioDto);
    }

     @GetMapping
     public ResponseEntity<List<RespostaTipoUsuarioDto>> listarTiposUsuario() {
         List<TipoUsuario> tiposUsuario = listarTodosTiposUsuarioController.listarTipoUsuarios();
        log.info("Listando todos os Tipos de Usuário!");
         List<RespostaTipoUsuarioDto> respostaTipoUsuarioDtos = tiposUsuario.stream().map(this::map).toList();
         return ResponseEntity.ok(respostaTipoUsuarioDtos);
     }
     @GetMapping("/{id}")
     public ResponseEntity<RespostaTipoUsuarioDto> buscarPorId(@PathVariable Long id) {
         TipoUsuario tipoUsuario = buscarTipoUsuarioController.buscarPorId(id);
        log.info("Tipo de Usuário encontrado: {}, id:", tipoUsuario.getId());
        return ResponseEntity.ok(map(tipoUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaTipoUsuarioDto> atualizar(
            @PathVariable Long id,
            @RequestBody CriarTipoUsuarioDto tipoUsuarioDto
    ){
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setNome(tipoUsuarioDto.getNome());
        tipoUsuario.setDescricao(tipoUsuarioDto.getDescricao());

        TipoUsuario atualizado = atualizarTipoUsuarioController.atualizar(id, tipoUsuario);
        log.info("Tipo de Usuário atualizado: {}, id: {}", atualizado.getDescricao(), atualizado.getId());
        return ResponseEntity.ok(map(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        deletarTipoUsuarioUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }



    public RespostaTipoUsuarioDto map(TipoUsuario tipoUsuario) {
        if(tipoUsuario == null) return null;
        return RespostaTipoUsuarioDto.builder()
                .id(tipoUsuario.getId())
                .nome(tipoUsuario.getNome())
                .descricao(tipoUsuario.getDescricao())
                .build();
	}
}

