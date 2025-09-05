package com.adjt.food_service_manager_clean_arch.infra.database.entity;

import com.adjt.food_service_manager_clean_arch.core.enums.TipoUsuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="usuarios")
@Data
@Builder
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String login;
    private String senha;
    private TipoUsuario tipoUsuario;

}

