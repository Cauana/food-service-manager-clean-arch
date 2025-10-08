package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.BuscarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.ListarTodosRestaurantesUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.ListarTodosTiposUsuarioUseCase;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.ListarTodosTiposUsuarioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.TipoUsuarioUseCaseImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tipo-usuario")
public class TipoUsuarioApiController {

    private final TipoUsuarioUseCaseImpl cadastrarTipoUsuarioController;
    private final ListarTodosTiposUsuarioUseCaseImpl listarTodosTiposUsuarioController;

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
    // @GetMapping("/{id}")
    // public ResponseEntity<RespostaTipoUsuarioDto> buscarPorId(@PathVariable Long id) {
    //     TipoUsuario tipoUsuario = buscarTipoUsuarioController.buscarTipoUsuario(id);
    //     log.info("Tipo de Usuário encontrado: {}, id:", tipoUsuario.getId());
    //     return ResponseEntity.ok(map(tipoUsuario));
    // }

    // public RespostaTipoUsuarioDto map(TipoUsuario tipoUsuario) {
    //     if(tipoUsuario == null) return null;
    //     return RespostaTipoUsuarioDto.builder()
    //             .id(tipoUsuario.getId())
    //             .nome(tipoUsuario.getNome())
    //             .build();
    // }

    public RespostaTipoUsuarioDto map(TipoUsuario tipoUsuario) {
        if(tipoUsuario == null) return null;
        return RespostaTipoUsuarioDto.builder()
                .id(tipoUsuario.getId())
                .nome(tipoUsuario.getNome())
                .descricao(tipoUsuario.getDescricao())
                .build();
	}
}

