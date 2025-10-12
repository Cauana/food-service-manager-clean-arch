package com.adjt.food_service_manager_clean_arch.infra.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.RestauranteEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.RestauranteRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.RestauranteEntityMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteJpaGatewayTest {

    @Mock
    private RestauranteRepository repository;

    @Mock
    private RestauranteEntityMapper mapper;

    @InjectMocks
    private RestauranteJpaGateway restauranteJpaGateway;

    private final Long RESTAURANTE_ID = 50L;
    private final String NOME_RESTAURANTE = "Churrascaria Teste";
    private Restaurante restauranteDomain;
    private RestauranteEntity restauranteEntity;

    @BeforeEach
    void setUp() {
        restauranteDomain = new Restaurante(
                RESTAURANTE_ID,
                NOME_RESTAURANTE,
                "Rua A",
                "Churrasco",
                "11h-22h",
                new Usuario(1L, "Dono Teste", "dono@teste.com", "123", "login", "senha", null, null)
        );

        restauranteEntity = new RestauranteEntity();
        restauranteEntity.setId(RESTAURANTE_ID);
        restauranteEntity.setNome(NOME_RESTAURANTE);
        restauranteEntity.setDonoRestaurante(new UsuarioEntity());
    }

    @Test
    @DisplayName("Deve mapear para Entidade, salvar e retornar o Domínio")
    void deveSalvarEConverterComSucesso() {
        when(mapper.toEntity(restauranteDomain)).thenReturn(restauranteEntity);
        when(repository.save(restauranteEntity)).thenReturn(restauranteEntity);
        when(mapper.toRestaurante(restauranteEntity)).thenReturn(restauranteDomain);

        Restaurante resultado = restauranteJpaGateway.criarRestaurante(restauranteDomain);

        assertNotNull(resultado);
        assertEquals(RESTAURANTE_ID, resultado.getId());

        verify(mapper, times(1)).toEntity(restauranteDomain);
        verify(repository, times(1)).save(restauranteEntity);
        verify(mapper, times(1)).toRestaurante(restauranteEntity);
    }

    @Test
    @DisplayName("Deve delegar a chamada para criarRestaurante()")
    void deveChamarCriarRestaurante() {
        when(mapper.toEntity(restauranteDomain)).thenReturn(restauranteEntity);
        when(repository.save(restauranteEntity)).thenReturn(restauranteEntity);
        when(mapper.toRestaurante(restauranteEntity)).thenReturn(restauranteDomain);

        Restaurante resultado = restauranteJpaGateway.salvar(restauranteDomain);

        assertNotNull(resultado);

        verify(mapper, times(1)).toEntity(restauranteDomain);
        verify(repository, times(1)).save(restauranteEntity);
        verify(mapper, times(1)).toRestaurante(restauranteEntity);
    }

    @Test
    @DisplayName("Deve mapear para Entidade e chamar delete no Repository")
    void deveMapearEChamarDelete() {
        when(mapper.toEntity(restauranteDomain)).thenReturn(restauranteEntity);
        doNothing().when(repository).delete(restauranteEntity);

        restauranteJpaGateway.deletar(restauranteDomain);

        verify(mapper, times(1)).toEntity(restauranteDomain);
        verify(repository, times(1)).delete(restauranteEntity);
    }

    @Test
    @DisplayName("Deve retornar Optional com Restaurante quando encontrado")
    void deveRetornarOptionalComRestaurante() {
        when(repository.findById(RESTAURANTE_ID)).thenReturn(Optional.of(restauranteEntity));
        when(mapper.toRestaurante(restauranteEntity)).thenReturn(restauranteDomain);


        Optional<Restaurante> resultado = restauranteJpaGateway.buscarPorId(RESTAURANTE_ID);

        assertTrue(resultado.isPresent());
        assertEquals(RESTAURANTE_ID, resultado.get().getId());
        verify(repository, times(1)).findById(RESTAURANTE_ID);
        verify(mapper, times(1)).toRestaurante(restauranteEntity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando não encontrado")
    void deveRetornarOptionalVazio() {
        when(repository.findById(RESTAURANTE_ID)).thenReturn(Optional.empty());

        Optional<Restaurante> resultado = restauranteJpaGateway.buscarPorId(RESTAURANTE_ID);

        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById(RESTAURANTE_ID);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve retornar lista de Restaurantes e mapear todos")
    void deveRetornarListaCompleta() {
        RestauranteEntity entity2 = new RestauranteEntity();
        Restaurante domain2 = new Restaurante(51L, "Pizzaria", "Rua B", "Pizza", "18h-00h", null);

        List<RestauranteEntity> entities = Arrays.asList(restauranteEntity, entity2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toRestaurante(restauranteEntity)).thenReturn(restauranteDomain);
        when(mapper.toRestaurante(entity2)).thenReturn(domain2);

        List<Restaurante> resultados = restauranteJpaGateway.buscarTodosRestaurantes();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals(NOME_RESTAURANTE, resultados.get(0).getNome());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toRestaurante(any(RestauranteEntity.class));
    }
}