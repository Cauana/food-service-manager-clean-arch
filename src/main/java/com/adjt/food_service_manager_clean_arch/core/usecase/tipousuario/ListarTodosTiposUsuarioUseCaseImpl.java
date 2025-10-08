package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.TipoUsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ListarTodosTiposUsuarioUseCaseImpl implements ListarTodosTiposUsuarioUseCase{

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public ListarTodosTiposUsuarioUseCaseImpl(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }


    @Override
    public List<TipoUsuario> listarTipoUsuarios() {
        return tipoUsuarioRepository.findAll()
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private TipoUsuario toDomain(TipoUsuarioEntity entity){
        return new TipoUsuario(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao()
        );
    }
}
