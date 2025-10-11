package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AtualizarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public Restaurante atualizarRestaurante(Long id, CriarRestauranteDto criarRestauranteDto){
        Restaurante existente = restauranteGateway.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Restaurante não encontrado"));
        existente.setNome(criarRestauranteDto.getNome());
        existente.setEndereco(criarRestauranteDto.getEndereco());
        existente.setTipoCozinha(criarRestauranteDto.getTipoCozinha());
        existente.setHorarioFuncionamento(criarRestauranteDto.getHorarioFuncionamento());

        if(criarRestauranteDto.getIdDonoRestaurante() != null){
            Usuario dono = usuarioGateway.buscarPorId(criarRestauranteDto.getIdDonoRestaurante())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,"Usuário dono não encontrado."
                    ));
            existente.setDonoRestaurante(dono);
        }
        return restauranteGateway.salvar(existente);
    }
}
