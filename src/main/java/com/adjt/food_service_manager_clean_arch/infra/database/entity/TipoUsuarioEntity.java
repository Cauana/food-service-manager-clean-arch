package com.adjt.food_service_manager_clean_arch.infra.database.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tipo_usuario")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_usuario_seq")
    @SequenceGenerator(name = "tipo_usuario_seq", sequenceName = "tipo_usuario_seq", allocationSize = 1)
    private Long id;
    private String nome;
    private String descricao;

}