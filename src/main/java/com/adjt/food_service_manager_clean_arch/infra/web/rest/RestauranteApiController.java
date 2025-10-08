package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.BuscarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.ListarTodosRestaurantesUseCaseImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurante")
public class RestauranteApiController {

    private final CadastrarRestauranteUseCaseImpl restauranteController;
    private final BuscarRestauranteUseCaseImpl buscarRestauranteController;
    private final ListarTodosRestaurantesUseCaseImpl listarTodosRestaurantesController;

    @PostMapping
    public ResponseEntity<RespostaRestauranteDto> criarRestaurante(@RequestBody CriarRestauranteDto restauranteDto, HttpSession session) {
        Restaurante restaurante = restauranteController.criarRestaurante(restauranteDto, session);
        log.info("Restaurante criado com ID: {}, nome: {}, dono: {}", restaurante.getId(), restaurante.getNome(), restaurante.getDonoRestaurante().getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(map(restaurante));
    }

    @GetMapping
    public ResponseEntity<List<RespostaRestauranteDto>> listarRestaurantes() {
        List<Restaurante> restaurantes = listarTodosRestaurantesController.buscarTodosRestaurantes();
        log.info("Listando todos os Restaurante!");
        List<RespostaRestauranteDto> respostaRestauranteDtos = restaurantes.stream().map(this::map).toList();
        return ResponseEntity.ok(respostaRestauranteDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RespostaRestauranteDto> buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = buscarRestauranteController.buscarRestaurante(id);
        log.info("Restaurante encontrado: {}, id:", restaurante.getId());
        return ResponseEntity.ok(map(restaurante));
    }

    public RespostaRestauranteDto map(Restaurante restaurante) {
        if(restaurante == null) return null;
        Usuario donoRestaurante = restaurante.getDonoRestaurante();
        String nomeDono = (donoRestaurante != null ) ? donoRestaurante.getNome() : "Dono do Restaurante Não Encontrado";
		return RespostaRestauranteDto.builder()
                .id(restaurante.getId())
				.nome(restaurante.getNome())
				.endereco(restaurante.getEndereco())
				.tipoCozinha(restaurante.getTipoCozinha())
                .horarioFuncionamento(restaurante.getHorarioFuncionamento())
				.nomeDonoRestaurante(nomeDono)
				.build();
	}
}
