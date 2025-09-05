package com.adjt.food_service_manager_clean_arch.core.dto;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CriarUsuarioRequestDto {
    private String nome;
    private String email;
    private String cpf;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;
}
