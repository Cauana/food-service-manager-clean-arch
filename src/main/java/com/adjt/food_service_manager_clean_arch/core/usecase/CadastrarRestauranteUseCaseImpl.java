package com.adjt.food_service_manager_clean_arch.core.usecase;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public Restaurante criarRestaurante(CriarRestauranteDto novoRestaurante) {
        Usuario dono = usuarioGateway.buscarPorId(novoRestaurante.getIdDonoRestaurante())
                        .orElseThrow(() -> new RuntimeException("Usuário dono não encontrado"));

        //validação dono restaurante
        if(dono.getTipoUsuario() != TipoUsuario.DONO_RESTAURANTE){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Somente usuários do tipo DONO_RESTAURANTE é possível realizar o cadastro de restaurante");
        }
        return restauranteGateway.criarRestaurante(map(novoRestaurante, dono));
    }

    public Restaurante map(CriarRestauranteDto dto, Usuario dono) {

		return Restaurante.builder()
				.nome(dto.getNome())
				.endereco(dto.getEndereco())
				.tipoCozinha(dto.getTipoCozinha())
                .horarioFuncionamento(dto.getHorarioFuncionamento())
				.donoRestaurante(dono)
				.build();
	}
}