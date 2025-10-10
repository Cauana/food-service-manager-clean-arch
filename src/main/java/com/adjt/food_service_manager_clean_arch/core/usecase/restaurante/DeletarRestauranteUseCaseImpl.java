package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DeletarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;

    public void deletarRestaurante(Long id){
        Restaurante restaurante = restauranteGateway.buscarPorId(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Restaurante não encontrado."
                ));
        restauranteGateway.deletar(restaurante);
    }
}
