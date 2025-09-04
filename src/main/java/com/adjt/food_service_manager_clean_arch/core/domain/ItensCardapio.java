package com.adjt.food_service_manager_clean_arch.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ItensCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nomeItem;
    private String descricaoItem;
    private Double precoItem;
    private Boolean disponibilidadeItem;
    private String fotoItem;
    private Restaurante restaurante;
}