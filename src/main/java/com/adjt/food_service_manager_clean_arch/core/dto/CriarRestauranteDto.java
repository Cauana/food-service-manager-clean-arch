package com.adjt.food_service_manager_clean_arch.core.dto;

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
public class CriarRestauranteDto {
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private Long donoRestaurante;
}
