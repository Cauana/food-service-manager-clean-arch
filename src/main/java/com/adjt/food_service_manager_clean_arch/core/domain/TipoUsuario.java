package com.adjt.food_service_manager_clean_arch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TipoUsuario {
    private Long id;
    private String nome;
    private String descricao;

    public TipoUsuario() {

    }
}
