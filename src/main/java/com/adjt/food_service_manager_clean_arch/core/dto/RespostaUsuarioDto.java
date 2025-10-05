package com.adjt.food_service_manager_clean_arch.core.dto;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespostaUsuarioDto {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String login;
    private String senha;
    private String tipoUsuario;
}

