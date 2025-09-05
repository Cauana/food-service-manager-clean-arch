package com.adjt.food_service_manager_clean_arch.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarUsuarioResponseDto {

    private Long id;
    private String nome;
    private String email;
    private String tipoUsuario;
}
