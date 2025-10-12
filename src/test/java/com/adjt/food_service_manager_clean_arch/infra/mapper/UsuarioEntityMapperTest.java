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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuarioEntityMapperTest {

    @InjectMocks
    private UsuarioEntityMapper usuarioEntityMapper;

    private final Long USER_ID = 5L;
    private final String NOME = "Carlos Teste";
    private final String CPF = "12345678900";

    private Usuario usuarioDomain;
    private UsuarioEntity usuarioEntity;
    private RestauranteEntity restauranteEntity1;
    private List<Restaurante> restaurantesDomain;
    private final String tipoDono = "DONO_RESTAURANTE";


    @BeforeEach
    void setUp() {
        Restaurante restauranteDomain1 = new Restaurante(10L, "Rest A", "End A", "Tipo A", "Horario A", null);
        restaurantesDomain = List.of(restauranteDomain1);

        usuarioDomain = Usuario.builder()
                .id(USER_ID)
                .nome(NOME)
                .email("teste@email.com")
                .cpf(CPF)
                .login("carlos.login")
                .senha("hash_senha")
                .tipoUsuario(tipoDono)
                .restaurantes(restaurantesDomain)
                .build();

        restauranteEntity1 = new RestauranteEntity();
        restauranteEntity1.setId(10L);
        restauranteEntity1.setNome("Rest A");
        restauranteEntity1.setEndereco("End A");
        restauranteEntity1.setTipoCozinha("Tipo A");
        restauranteEntity1.setHorarioFuncionamento("Horario A");

        usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(USER_ID);
        usuarioEntity.setNome(NOME);
        usuarioEntity.setEmail("teste@email.com");
        usuarioEntity.setCpf(CPF);
        usuarioEntity.setLogin("carlos.login");
        usuarioEntity.setSenha("hash_senha");
        usuarioEntity.setTipoUsuario(tipoDono);
        usuarioEntity.setRestaurantes(List.of(restauranteEntity1));
    }

    @Test
    @DisplayName("Deve mapear todos os campos do Domínio para Entidade")
    void deveMapearCamposCorretamente() {
        UsuarioEntity resultado = usuarioEntityMapper.toEntity(usuarioDomain);

        assertNotNull(resultado);
        assertEquals(USER_ID, resultado.getId());
        assertEquals(NOME, resultado.getNome());
        assertEquals(CPF, resultado.getCpf());
        assertEquals(tipoDono, resultado.getTipoUsuario());

        assertNull(resultado.getRestaurantes());
    }

    @Test
    @DisplayName("Deve retornar null se o objeto de Domínio for null")
    void deveRetornarNullSeDominioForNulo() {
        assertNull(usuarioEntityMapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve mapear todos os campos e a lista aninhada de Restaurantes")
    void deveMapearCamposERestaurantesCorretamente() {
        Usuario resultado = usuarioEntityMapper.toUsuario(usuarioEntity);

        assertNotNull(resultado);
        assertEquals(USER_ID, resultado.getId());
        assertEquals(NOME, resultado.getNome());
        assertEquals(tipoDono, resultado.getTipoUsuario());

        assertFalse(resultado.getRestaurantes().isEmpty());
        assertEquals(1, resultado.getRestaurantes().size());

        Restaurante restResultado = resultado.getRestaurantes().get(0);
        assertEquals(restauranteEntity1.getId(), restResultado.getId());
        assertEquals(restauranteEntity1.getNome(), restResultado.getNome());
        assertEquals(restauranteEntity1.getEndereco(), restResultado.getEndereco());

        assertNull(restResultado.getDonoRestaurante());
    }

    @Test
    @DisplayName("Deve retornar lista vazia de Restaurantes se a Entidade tiver lista nula")
    void deveRetornarListaVaziaSeRestaurantesForNulo() {
        usuarioEntity.setRestaurantes(null);

        Usuario resultado = usuarioEntityMapper.toUsuario(usuarioEntity);

        assertNotNull(resultado);
        assertNotNull(resultado.getRestaurantes());
        assertTrue(resultado.getRestaurantes().isEmpty());
    }

    @Test
    @DisplayName("deve retornar null se o objeto de Entidade for null")
    void deveRetornarNullSeEntidadeForNula() {
        assertNull(usuarioEntityMapper.toUsuario(null));
    }
}