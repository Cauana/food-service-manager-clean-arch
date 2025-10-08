package com.adjt.food_service_manager_clean_arch.core.dto;
import java.util.List;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String mensagem;
    private String cpf;
    private String nome;
    private String tipoUsuario;
    private List<Long> restaurantesIds;
    
}
