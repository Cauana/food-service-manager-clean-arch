package com.adjt.food_service_manager_clean_arch.core.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemCardapio {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidade;
    private String foto;
    private Restaurante restaurante;
}