package com.adjt.food_service_manager_clean_arch.core.dto;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;

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
public class CriarItemCardapioDto {
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidade;
    private String foto;
    private Long idRestaurante;
    
}
