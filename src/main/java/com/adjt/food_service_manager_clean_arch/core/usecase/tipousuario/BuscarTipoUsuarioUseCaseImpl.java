package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarTipoUsuarioUseCaseImpl implements BuscarTipoUsuarioUseCase{
    private final TipoUsuarioGateway tipoUsuarioGateway;

    public BuscarTipoUsuarioUseCaseImpl(TipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    @Override
    public TipoUsuario buscarPorId(Long id){
        return tipoUsuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de usuário com id " + id + " não encontrado."));
    }
}
