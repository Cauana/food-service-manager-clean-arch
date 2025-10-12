package com.adjt.food_service_manager_clean_arch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String login;
    private String senha;
    private String tipoUsuario;
    private List<Restaurante> restaurantes;
}




