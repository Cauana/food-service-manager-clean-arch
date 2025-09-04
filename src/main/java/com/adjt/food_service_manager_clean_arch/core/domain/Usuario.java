package com.adjt.food_service_manager_clean_arch.core.domain;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;
    private String cpf;
    private TipoUsuario tipoUsuario;
}




