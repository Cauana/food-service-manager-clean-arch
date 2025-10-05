package com.adjt.food_service_manager_clean_arch.core.domain;

import java.util.List;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private TipoUsuario tipoUsuario;
    private List<Restaurante> restaurantes;
}




