package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public Restaurante criarRestaurante(CriarRestauranteDto novoRestaurante) {
        Usuario dono = usuarioGateway.buscarPorCpf(novoRestaurante.getDonoRestaurante().toString())
                        .orElseThrow(() -> new RuntimeException("Usuário dono não encontrado"));

        return restauranteGateway.criarRestaurante(map(novoRestaurante, dono.getId()));
    }

    public Restaurante map(CriarRestauranteDto dto, Long donoId) {

		return Restaurante.builder()
				.nome(dto.getNome())
				.endereco(dto.getEndereco())
				.tipoCozinha(dto.getTipoCozinha())
				.donoRestaurante(donoId)
				.build();
	}
}