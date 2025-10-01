package com.adjt.food_service_manager_clean_arch.infra.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="item_cardapio")
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
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    private RestauranteEntity restaurante;

}
