package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
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
    public Restaurante criarRestaurante(@RequestBody CriarRestauranteDto restauranteDto) {
        Restaurante restaurante = restauranteController.criarRestaurante(restauranteDto);
        log.info("Restaurante criado com ID: {}", restaurante.getId());
        return restaurante;
    }

    public CriarRestauranteDto map(Restaurante restaurante) {

		return CriarRestauranteDto.builder()
				.nome(restaurante.getNome())
				.endereco(restaurante.getEndereco())
				.tipoCozinha(restaurante.getTipoCozinha())
				.donoRestaurante(restaurante.getDonoRestaurante())
				.build();
	}
}
