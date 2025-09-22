package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarItemCardapioUseCaseImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/item-cardapio")
public class ItemCardapioApiController {

    private final CadastrarItemCardapioUseCaseImpl itemCardapioController;

    @PostMapping
    public RespostaItemCardapioDto criarItemCardapio(@RequestBody CriarItemCardapioDto itemCardapioDto) {
        ItemCardapio itemCardapio = itemCardapioController.criarItemCardapio(itemCardapioDto);
        log.info("Item de cardápio criado com ID: {}, nome: {}", itemCardapio.getId(), itemCardapio.getNome());
        return map(itemCardapio);
    }

    public RespostaItemCardapioDto map(ItemCardapio itemCardapio) {

		return RespostaItemCardapioDto.builder()
                .id(itemCardapio.getId())
				.nome(itemCardapio.getNome())
				.descricao(itemCardapio.getDescricao())
				.preco(itemCardapio.getPreco())
				.nomeRestaurante(itemCardapio.getRestaurante().getNome())
				.build();
	}
}
