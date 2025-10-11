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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Restaurante não encontrado"));


        Object tipoUsuarioObj = session.getAttribute("tipoUsuario");
        Object cpfUsuarioObj = session.getAttribute("cpfUsuario");

        //verifica usuário na sessão
        if (tipoUsuarioObj == null || cpfUsuarioObj == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado na sessão");
        }

        //verifica tipo de usuário
        String tipoUsuario = tipoUsuarioObj.toString();
        String cpfUsuario = cpfUsuarioObj.toString();

        if (!"DONO_RESTAURANTE".equals(tipoUsuario) || !restaurante.getDonoRestaurante().getCpf().equals(cpfUsuario)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente o perfil DONO_RESTAURANTE pode cadastrar itens de cardápio.");
        }
        ItemCardapio novoItem = map(item, restaurante);
        return itemCardapioGateway.criarItemCardapio(novoItem);
    }

    private ItemCardapio map(CriarItemCardapioDto item, Restaurante restaurante) {
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