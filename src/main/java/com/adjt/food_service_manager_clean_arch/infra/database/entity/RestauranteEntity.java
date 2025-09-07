package com.adjt.food_service_manager_clean_arch.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="restaurantes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_seq")
    @SequenceGenerator(name = "restaurante_seq", sequenceName = "restaurante_seq", allocationSize = 1)
    private Long id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private Long idDonoRestaurante;

}
