package com.adjt.food_service_manager_clean_arch.core.domain;


import org.junit.jupiter.api.Test;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;

public class UsuarioTest {

    @Test
    public void testUsuario() {
    String nome = "João Silva";
    String email = "joao.silva@example.com";
    String cpf = "123.456.789-00";
    String login = "joaosilva";
    String senha = "senha123";
    TipoUsuario tipoUsuario = TipoUsuario.builder()
            .id(1l)
            .nome("Dono")
            .descricao("DONO_RESTAURANTE")
            .build();

    var usuario = Usuario.builder()
            .nome(nome)
            .email(email)
            .cpf(cpf)
            .login(login)
            .senha(senha)
            .tipoUsuario(tipoUsuario.getNome())
            .build();

    assert usuario.getNome().equals(nome);
    assert usuario.getEmail().equals(email);
    assert usuario.getCpf().equals(cpf);
    assert usuario.getLogin().equals(login);
    assert usuario.getSenha().equals(senha);
    assert usuario.getTipoUsuario().equals(tipoUsuario.getNome());
    }

}
