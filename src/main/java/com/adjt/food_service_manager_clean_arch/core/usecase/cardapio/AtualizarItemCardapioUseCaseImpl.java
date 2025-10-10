package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AtualizarItemCardapioUseCaseImpl {
    private final ItemCardapioGateway itemCardapioGateway;

    public ItemCardapio atualizar (Long id, CriarItemCardapioDto cardapioDto){
        ItemCardapio existente = itemCardapioGateway.buscarPorId(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Item do cardápio não encontrado"
                ));
        existente.setNome(cardapioDto.getNome());
        existente.setDescricao(cardapioDto.getDescricao());
        existente.setPreco(cardapioDto.getPreco());
        existente.setDisponibilidade(cardapioDto.getDisponibilidade());
        existente.setFoto(cardapioDto.getFoto());

        return itemCardapioGateway.salvar(existente);
    }

}
