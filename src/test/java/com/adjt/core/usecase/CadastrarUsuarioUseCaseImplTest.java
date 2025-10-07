package com.adjt.core.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.CadastrarUsuarioUseCaseImpl;

public class CadastrarUsuarioUseCaseImplTest {

    @Test
    public void testCadastrarUsuario() {
        String nome = "João Silva";
        String email = "joao.silva@example.com";
        String cpf = "123.456.789-00";
        String login = "joaosilva";
        String senha = "senha123";
        TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

        CriarUsuarioDto novoUsuario = CriarUsuarioDto.builder()
                .nome(nome)
                .email(email)
                .cpf(cpf)
                .login(login)
                .senha(senha)
                .tipoUsuario(tipoUsuario)
                .build();

        UsuarioGateway usuarioGateway = mock(UsuarioGateway.class);
        when(usuarioGateway.criar(any())).thenReturn(
                Usuario.builder()
                        .nome(nome)
                        .email(email)
                        .cpf(cpf)
                        .login(login)
                        .senha(senha)
                        .tipoUsuario(tipoUsuario)
                        .build()
        );
        CadastrarUsuarioUseCaseImpl cadastrarUsuarioUseCase = new CadastrarUsuarioUseCaseImpl(usuarioGateway);
        
        Usuario usuarioCriado = cadastrarUsuarioUseCase.criarUsuario(novoUsuario);
        assertNotNull(usuarioCriado);
        assert usuarioCriado.getNome().equals(nome);
        assert usuarioCriado.getEmail().equals(email);
        assert usuarioCriado.getCpf().equals(cpf);
        assert usuarioCriado.getLogin().equals(login);
        assert usuarioCriado.getSenha().equals(senha);
        assert usuarioCriado.getTipoUsuario() == tipoUsuario;

    }
}
