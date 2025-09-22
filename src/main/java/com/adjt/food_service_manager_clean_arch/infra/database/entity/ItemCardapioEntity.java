package com.adjt.food_service_manager_clean_arch.infra.database.entity;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
public class ItemCardapioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_cardapio_seq")
    @SequenceGenerator(name = "item_cardapio_seq", sequenceName = "item_cardapio_seq", allocationSize = 1)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponibilidade;
    private String foto;
    @ManyToOne
    @JoinColumn(name = "restaurante", referencedColumnName = "id")
    private RestauranteEntity restaurante;
}
