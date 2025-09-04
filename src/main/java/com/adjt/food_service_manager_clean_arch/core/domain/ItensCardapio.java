package com.adjt.foodservicemanagercleanarch.core.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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