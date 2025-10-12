package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TipoUsuarioUseCaseImpl {
    private final TipoUsuarioGateway tipoUsuarioGateway;

    public TipoUsuario criarTipoUsuario(CriarTipoUsuarioDto tipoDto) {

        return tipoUsuarioGateway.criarTipoUsuario(map(tipoDto));
    }

    public TipoUsuario map(CriarTipoUsuarioDto dto) {

		return TipoUsuario.builder()
				.nome(dto.getNome())
                .descricao(dto.getDescricao())
				.build();
	}

}
