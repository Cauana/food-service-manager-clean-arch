package com.adjt.food_service_manager_clean_arch.core.domain;

import java.time.LocalDate;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String cpf;
    private LocalDate dataNascimento;
    private TipoUsuario tipoUsuario;
}




