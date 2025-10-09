package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AtualizarUsuarioUseCaseImpl {

    private final UsuarioGateway usuarioGateway;

    public Usuario atualizarUsuario(Long id, CriarUsuarioDto usuarioDto){
        Usuario existente = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Usuário não encontrado."
                ));
        existente.setNome(usuarioDto.getNome());
        existente.setEmail(usuarioDto.getEmail());
        existente.setCpf(usuarioDto.getCpf());
        existente.setLogin(usuarioDto.getLogin());
        existente.setSenha(usuarioDto.getSenha());
        existente.setTipoUsuario(usuarioDto.getTipoUsuario());

        return usuarioGateway.criar(existente);
    }
}
