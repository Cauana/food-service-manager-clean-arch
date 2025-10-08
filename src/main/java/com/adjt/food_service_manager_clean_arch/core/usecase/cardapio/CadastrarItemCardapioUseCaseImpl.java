package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CadastrarItemCardapioUseCaseImpl {
    private final ItemCardapioGateway itemCardapioGateway;
    private final RestauranteGateway restauranteGateway;

    public ItemCardapio criarItemCardapio(CriarItemCardapioDto item, HttpSession session) {
        Restaurante restaurante = restauranteGateway.buscarPorId(item.getIdRestaurante())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        ItemCardapio novoItem = map(item, restaurante);
        //validação dono restaurante
        if(!session.getAttribute("tipoUsuario").equals("DONO_RESTAURANTE") || !novoItem.getRestaurante().getDonoRestaurante().getCpf().equals(session.getAttribute("cpfUsuario"))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Somente usuários do tipo DONO_RESTAURANTE é possível realizar o cadastro de restaurante");
        }
        return itemCardapioGateway.criarItemCardapio(novoItem);
    }

    public ItemCardapio map(CriarItemCardapioDto item, Restaurante restaurante) {
        ItemCardapio itemCardapio = ItemCardapio.builder()
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .preco(item.getPreco())
                .disponibilidade(item.getDisponibilidade())
                .foto(item.getFoto())
                .restaurante(restaurante)
                .build();

        return itemCardapio;
    }
}