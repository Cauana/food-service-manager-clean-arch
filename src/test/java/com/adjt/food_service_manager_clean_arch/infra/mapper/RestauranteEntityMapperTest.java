package com.adjt.food_service_manager_clean_arch.infra.mapper;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;
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
class RestauranteEntityMapperTest {

    @Mock
    private UsuarioEntityMapper usuarioEntityMapper;

    @InjectMocks
    private RestauranteEntityMapper restauranteEntityMapper;

    private final Long RESTAURANTE_ID = 1L;
    private Restaurante restauranteDomain;
    private RestauranteEntity restauranteEntity;
    private Usuario donoDomain;
    private UsuarioEntity donoEntity;

    @BeforeEach
    void setUp() {
        donoDomain = new Usuario(10L, "Dono Domínio", null, null, null, null, null, null);
        restauranteDomain = new Restaurante(
                RESTAURANTE_ID,
                "Pizzaria Teste",
                "Rua Principal",
                "Italiana",
                "18h-00h",
                donoDomain
        );
        donoEntity = new UsuarioEntity();
        donoEntity.setId(10L);
        restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(RESTAURANTE_ID);
        restauranteEntity.setDonoRestaurante(donoEntity);
    }

    @Test
    @DisplayName("toEntity: Deve mapear todos os campos e delegar o mapeamento de DonoRestaurante")
    void deveMapearEDelegarDonoToEntity() {

        when(usuarioEntityMapper.toEntity(donoDomain)).thenReturn(donoEntity);

        RestauranteEntity resultado = restauranteEntityMapper.toEntity(restauranteDomain);

        assertNotNull(resultado);
        assertEquals(RESTAURANTE_ID, resultado.getId());
        assertEquals("Pizzaria Teste", resultado.getNome());
        assertEquals(donoEntity, resultado.getDonoRestaurante());

        verify(usuarioEntityMapper, times(1)).toEntity(donoDomain);
    }

    @Test
    @DisplayName("toEntity: Deve retornar null se o objeto de Domínio for null")
    void deveRetornarNullSeDominioForNuloToEntity() {
        assertNull(restauranteEntityMapper.toEntity(null));
        verifyNoInteractions(usuarioEntityMapper);
    }


    @Test
    @DisplayName("toRestaurante: Deve mapear todos os campos e delegar o mapeamento de DonoRestaurante")
    void deveMapearEDelegarDonoToRestaurante() {

        when(usuarioEntityMapper.toUsuario(donoEntity)).thenReturn(donoDomain);

        Restaurante resultado = restauranteEntityMapper.toRestaurante(restauranteEntity);

        assertNotNull(resultado);
        assertEquals(RESTAURANTE_ID, resultado.getId());
        assertEquals(donoDomain, resultado.getDonoRestaurante());

        verify(usuarioEntityMapper, times(1)).toUsuario(donoEntity);
    }

    @Test
    @DisplayName("toRestaurante: Deve retornar null se o objeto de Entidade for null")
    void deveRetornarNullSeEntidadeForNulaToRestaurante() {
        assertNull(restauranteEntityMapper.toRestaurante(null));
        verifyNoInteractions(usuarioEntityMapper);
    }
}