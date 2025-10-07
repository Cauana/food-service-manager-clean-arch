package com.adjt.core.domain;


import org.junit.jupiter.api.Test;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

public class UsuarioTest {

    @Test
    public void testUsuario() {
    String nome = "João Silva";
    String email = "joao.silva@example.com";
    String cpf = "123.456.789-00";
    String login = "joaosilva";
    String senha = "senha123";
    TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

    var usuario = Usuario.builder()
            .nome(nome)
            .email(email)
            .cpf(cpf)
            .login(login)
            .senha(senha)
            .tipoUsuario(tipoUsuario)
            .build();

    assert usuario.getNome().equals(nome);
    assert usuario.getEmail().equals(email);
    assert usuario.getCpf().equals(cpf);
    assert usuario.getLogin().equals(login);
    assert usuario.getSenha().equals(senha);
    assert usuario.getTipoUsuario() == tipoUsuario;
    }

}
