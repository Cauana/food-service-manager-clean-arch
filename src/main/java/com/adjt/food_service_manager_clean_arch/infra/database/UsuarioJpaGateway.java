package com.adjt.food_service_manager_clean_arch.infra.database;

import java.util.Optional;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.UsuarioRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class UsuarioJpaGateway implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioEntityMapper usuarioEntityMapper;

    @Override
    public Usuario criar(Usuario usuario) {
        try {
            UsuarioEntity usuarioEntity = usuarioEntityMapper.toEntity(usuario);
            usuarioRepository.save(usuarioEntity);
            return usuarioEntityMapper.toUsuario(usuarioEntity);
        } catch (Exception e) {
            log.error("Erro ao salvar usuário: {}", usuario, e);
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

	@Override
	public Optional<Usuario> buscarPorCpf(String cpf) {
		Optional<UsuarioEntity> usuarioEntityOp = usuarioRepository.findByCpf(cpf);
		var usuarioEntity = usuarioEntityOp.get();
		var usuario = usuarioEntityMapper.toUsuario(usuarioEntity);
		return Optional.of(usuario);
	}
}