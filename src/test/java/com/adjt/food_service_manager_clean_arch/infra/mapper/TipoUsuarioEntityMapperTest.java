package com.adjt.food_service_manager_clean_arch.infra.mapper;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioEntityMapperTest {

    @InjectMocks
    private TipoUsuarioEntityMapper tipoUsuarioEntityMapper;

    private final Long ID = 1L;
    private final String NOME = "ADMIN";
    private final String DESCRICAO = "Administrador do Sistema";

    private TipoUsuario tipoUsuarioDomain;
    private TipoUsuarioEntity tipoUsuarioEntity;

    @BeforeEach
    void setUp() {

        tipoUsuarioDomain = new TipoUsuario(ID, NOME, DESCRICAO);

        tipoUsuarioEntity = TipoUsuarioEntity.builder()
                .id(ID)
                .nome(NOME)
                .descricao(DESCRICAO)
                .build();
    }

    @Test
    @DisplayName("toEntity: Deve mapear todos os campos de Domínio para Entidade")
    void deveMapearCamposCorretamenteToEntity() {
        TipoUsuarioEntity resultado = tipoUsuarioEntityMapper.toEntity(tipoUsuarioDomain);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOME, resultado.getNome());
        assertEquals(DESCRICAO, resultado.getDescricao());
    }

    @Test
    @DisplayName("toEntity: Deve retornar null se o objeto de Domínio for null")
    void deveRetornarNullSeDominioForNulToEntity() {
        assertNull(tipoUsuarioEntityMapper.toEntity(null));
    }

    @Test
    @DisplayName("toTipoUsuario: Deve mapear todos os campos de Entidade para Domínio (Builder)")
    void deveMapearCamposCorretamenteToTipoUsuario() {

        TipoUsuario resultado = tipoUsuarioEntityMapper.toTipoUsuario(tipoUsuarioEntity);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOME, resultado.getNome());
        assertEquals(DESCRICAO, resultado.getDescricao());
    }

    @Test
    @DisplayName("toTipoUsuario: Deve retornar null se o objeto de Entidade for null")
    void deveRetornarNullSeEntidadeForNulaToTipoUsuario() {
        assertNull(tipoUsuarioEntityMapper.toTipoUsuario(null));
    }

    @Test
    @DisplayName("toDomain: Deve mapear todos os campos de Entidade para Domínio (Setters)")
    void deveMapearCamposCorretamenteToDomain() {
        TipoUsuario resultado = tipoUsuarioEntityMapper.toDomain(tipoUsuarioEntity);

        assertNotNull(resultado);
        assertEquals(ID, resultado.getId());
        assertEquals(NOME, resultado.getNome());
        assertEquals(DESCRICAO, resultado.getDescricao());
    }

    @Test
    @DisplayName("toDomain: Deve retornar null se o objeto de Entidade for null")
    void deveRetornarNullSeEntidadeForNulaToDomain() {
        assertNull(tipoUsuarioEntityMapper.toDomain(null));
    }
}