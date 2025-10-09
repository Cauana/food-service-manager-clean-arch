package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class CadastrarRestauranteUseCaseImpl {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    public Restaurante criarRestaurante(CriarRestauranteDto novoRestaurante, HttpSession session) {
        Usuario dono = usuarioGateway.buscarPorId(novoRestaurante.getIdDonoRestaurante())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário dono não encontrado"));

        Object tipoUsuarioObj = session.getAttribute("tipoUsuario");

        //validação dono restaurante
        if(tipoUsuarioObj == null || !"DONO_RESTAURANTE".equals(tipoUsuarioObj.toString())) {
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