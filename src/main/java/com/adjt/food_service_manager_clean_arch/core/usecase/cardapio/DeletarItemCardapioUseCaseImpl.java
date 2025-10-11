package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeletarItemCardapioUseCaseImpl {

    private final ItemCardapioGateway itemCardapioGateway;

    public void deletar(Long id, HttpSession session){
        ItemCardapio item = itemCardapioGateway.buscarPorId(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Item cardápio não encontrado."
                ));

        Object tipoUsuario = session.getAttribute("tipoUsuario");
        Object cpfUsuario = session.getAttribute("cpfUsuario");

        // somente o dono do restaurante pode deletar
        if(tipoUsuario == null || !"DONO_RESTAURANTE".equals(tipoUsuario.toString())){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Somente usuários do tipo DONO_RESTAURANTE podem excluir itens do cardápio."
            );
        }

        if(cpfUsuario == null || !item.getRestaurante().getDonoRestaurante().getCpf().equals(cpfUsuario.toString())){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Usuário logado não tem permissão para excluir este item."
            );
        }
        itemCardapioGateway.deletar(item);
    }
}
