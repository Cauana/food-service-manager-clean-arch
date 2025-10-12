package com.adjt.food_service_manager_clean_arch.infra.mapper;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class ItemCardapioEntityMapperTest {

        @Mock
        private RestauranteEntityMapper restauranteEntityMapper;

        @InjectMocks
        private ItemCardapioEntityMapper itemCardapioEntityMapper;

        private final Long ITEM_ID = 1L;
        private ItemCardapio itemDomain;
        private ItemCardapioEntity itemEntity;
        private Restaurante restauranteDomain;
        private RestauranteEntity restauranteEntity;

        @BeforeEach
        void setUp() {
            restauranteDomain = new Restaurante(10L, "Rest Domínio", null, null, null, null);
            itemDomain = new ItemCardapio(
                    ITEM_ID,
                    "Lasanha",
                    "Massa e queijo",
                    45.5,
                    true,
                    "foto.png",
                    restauranteDomain
            );

            restauranteEntity = new RestauranteEntity();
            restauranteEntity.setId(10L);
            itemEntity = new ItemCardapioEntity();
            itemEntity.setId(ITEM_ID);
            itemEntity.setRestaurante(restauranteEntity);
        }

        @Test
        @DisplayName("toEntity: Deve mapear todos os campos e delegar o mapeamento de Restaurante")
        void deveMapearEDelegarRestauranteToEntity() {
            when(restauranteEntityMapper.toEntity(restauranteDomain)).thenReturn(restauranteEntity);


            ItemCardapioEntity resultado = itemCardapioEntityMapper.toEntity(itemDomain);


            assertNotNull(resultado);
            assertEquals(ITEM_ID, resultado.getId());
            assertEquals("Lasanha", resultado.getNome());
            assertEquals(restauranteEntity, resultado.getRestaurante());

            verify(restauranteEntityMapper, times(1)).toEntity(restauranteDomain);
        }

        @Test
        @DisplayName("toEntity: Deve retornar null se o objeto de Domínio for null")
        void deveRetornarNullSeDominioForNuloToEntity() {
            assertNull(itemCardapioEntityMapper.toEntity(null));
            verifyNoInteractions(restauranteEntityMapper);
        }

        @Test
        @DisplayName("toItemCardapio: Deve mapear todos os campos e delegar o mapeamento de Restaurante")
        void deveMapearEDelegarRestauranteToItemCardapio() {

            when(restauranteEntityMapper.toRestaurante(restauranteEntity)).thenReturn(restauranteDomain);

            ItemCardapio resultado = itemCardapioEntityMapper.toItemCardapio(itemEntity);

            assertNotNull(resultado);
            assertEquals(ITEM_ID, resultado.getId());
            assertEquals(restauranteDomain, resultado.getRestaurante());

            verify(restauranteEntityMapper, times(1)).toRestaurante(restauranteEntity);
        }

        @Test
        @DisplayName("toItemCardapio: Deve retornar null se o objeto de Entidade for null")
        void deveRetornarNullSeEntidadeForNulaToItemCardapio() {
            assertNull(itemCardapioEntityMapper.toItemCardapio(null));
            verifyNoInteractions(restauranteEntityMapper);
        }
    }