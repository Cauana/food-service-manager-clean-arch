package com.adjt.food_service_manager_clean_arch.infra.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.TipoUsuarioRepository;

import com.adjt.food_service_manager_clean_arch.infra.mapper.TipoUsuarioEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TipoUsuarioJpaGateway implements TipoUsuarioGateway {

    private final TipoUsuarioRepository repository;
    private final TipoUsuarioEntityMapper mapper;

    @Override
    public TipoUsuario criarTipoUsuario(TipoUsuario tipoUsuario) {
        TipoUsuarioEntity entity = mapper.toEntity(tipoUsuario);
        repository.save(entity);
        return mapper.toTipoUsuario(entity);
    }

    @Override
    public Optional<TipoUsuario> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toTipoUsuario);
    }

    @Override
    public List<TipoUsuario> buscarTodosTiposUsuario() {
        return repository.findAll().stream()
                .map(mapper::toTipoUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipoUsuario) {
        return mapper.toDomain(repository.save(mapper.toEntity(tipoUsuario)));
    }

}