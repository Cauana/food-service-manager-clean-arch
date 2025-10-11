package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DeletarTipoUsuarioUseCaseImpl implements DeletarTipoUsuarioUseCase{

    private final TipoUsuarioGateway tipoUsuarioGateway;

    public DeletarTipoUsuarioUseCaseImpl(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    @Override
    public void deletar(Long id){
        TipoUsuario existente = tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de usuário com id " + id + " não encontrado."
                ));
        tipoUsuarioGateway.deletar(existente);
    }
}
