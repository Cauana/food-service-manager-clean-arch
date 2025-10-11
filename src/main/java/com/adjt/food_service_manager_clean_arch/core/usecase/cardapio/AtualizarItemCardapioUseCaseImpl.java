package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AtualizarItemCardapioUseCaseImpl {
    private final ItemCardapioGateway itemCardapioGateway;

    public ItemCardapio atualizar (Long id, CriarItemCardapioDto cardapioDto, HttpSession session){
        ItemCardapio existente = itemCardapioGateway.buscarPorId(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Item do cardápio não encontrado"
                ));

        //Apenas dono do restaurante poderá alterar
        String tipoUsuario = (String)session.getAttribute("tipoUsuario");
        String cpfUsuario = (String) session.getAttribute("cpfUsuario");

        if(!"DONO_RESTAURANTE".equals(tipoUsuario) ||
        !existente.getRestaurante().getDonoRestaurante().getCpf().equals(cpfUsuario)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente o dono do restaurante pode atualizar este item do cardápio"
            );
        }

        existente.setNome(cardapioDto.getNome());
        existente.setDescricao(cardapioDto.getDescricao());
        existente.setPreco(cardapioDto.getPreco());
        existente.setDisponibilidade(cardapioDto.getDisponibilidade());
        existente.setFoto(cardapioDto.getFoto());

        return itemCardapioGateway.salvar(existente);
    }

}
