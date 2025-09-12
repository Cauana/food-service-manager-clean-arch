package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarRestauranteUseCaseImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurante")
public class RestauranteApiController {

    private final CadastrarRestauranteUseCaseImpl restauranteController;

    @PostMapping
    public RespostaRestauranteDto criarRestaurante(@RequestBody CriarRestauranteDto restauranteDto) {
        Restaurante restaurante = restauranteController.criarRestaurante(restauranteDto);
        log.info("Restaurante criado com ID: {}, nome: {}, dono: {}", restaurante.getId(), restaurante.getNome(), restaurante.getDonoRestaurante().getNome());
        return map(restaurante);
    }

    public RespostaRestauranteDto map(Restaurante restaurante) {

		return RespostaRestauranteDto.builder()
                .id(restaurante.getId())
				.nome(restaurante.getNome())
				.endereco(restaurante.getEndereco())
				.tipoCozinha(restaurante.getTipoCozinha())
				.nomeDonoRestaurante(restaurante.getDonoRestaurante().getNome())
				.build();
	}
}
