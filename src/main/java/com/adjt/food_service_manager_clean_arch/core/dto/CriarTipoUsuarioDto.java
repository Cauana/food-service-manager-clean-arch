package com.adjt.food_service_manager_clean_arch.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CriarTipoUsuarioDto {
    private String nome;
    private String descricao;
}
