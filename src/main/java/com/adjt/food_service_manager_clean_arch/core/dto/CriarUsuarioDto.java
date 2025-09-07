package com.adjt.food_service_manager_clean_arch.core.dto;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioDto {
    private String nome;
    private String email;
    private String cpf;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;
}
