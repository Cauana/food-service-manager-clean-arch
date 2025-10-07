package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CadastrarItemCardapioUseCaseImpl {
    private final ItemCardapioGateway itemCardapioGateway;
    private final RestauranteGateway restauranteGateway;

    public ItemCardapio criarItemCardapio(CriarItemCardapioDto item) {
        Restaurante restaurante = restauranteGateway.buscarPorId(item.getIdRestaurante())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        ItemCardapio itemCardapio = ItemCardapio.builder()
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .disponibilidade(item.getDisponibilidade())
                .foto(item.getFoto())
                .restaurante(restaurante)
                .build();

        return itemCardapioGateway.criarItemCardapio(itemCardapio);
    }

    public ItemCardapio map(CriarItemCardapioDto dto, Restaurante restaurante) {

		return ItemCardapio.builder()
				.nome(dto.getNome())
                .descricao(dto.getDescricao())
                .preco(dto.getPreco())
				.disponibilidade(dto.getDisponibilidade())
                .foto(dto.getFoto())
                .restaurante(restaurante)
				.build();
	}
}