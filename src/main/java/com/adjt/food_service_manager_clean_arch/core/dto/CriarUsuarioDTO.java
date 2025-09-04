package com.adjt.food_service_manager.core.dto;

import java.time.LocalDate;

import com.adjt.food_service_manager.core.enums.PerfilUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CriarUsuarioDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String cpf;
    private LocalDate dataNascimento;
    private PerfilUsuario perfil;

}
