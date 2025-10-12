package com.adjt.food_service_manager_clean_arch.infra.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.TipoUsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.TipoUsuarioRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.TipoUsuarioEntityMapper;

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
    class TipoUsuarioJpaGatewayTest {

        @Mock
        private TipoUsuarioRepository repository;

        @Mock
        private TipoUsuarioEntityMapper mapper;

        @InjectMocks
        private TipoUsuarioJpaGateway tipoUsuarioJpaGateway;

        private final Long TIPO_USUARIO_ID = 1L;
        private final String NOME_TIPO = "ADMIN";
        private TipoUsuario tipoUsuarioDomain;
        private TipoUsuarioEntity tipoUsuarioEntity;

        @BeforeEach
        void setUp() {

            tipoUsuarioDomain = new TipoUsuario(TIPO_USUARIO_ID, NOME_TIPO, "Descricao Teste");

            tipoUsuarioEntity = new TipoUsuarioEntity();
            tipoUsuarioEntity.setId(TIPO_USUARIO_ID);
            tipoUsuarioEntity.setNome(NOME_TIPO);
        }

        @Test
        @DisplayName("Deve mapear para entidade, salvar e retornar o domínio")
        void deveSalvarEConverterComSucesso() {

            when(mapper.toEntity(tipoUsuarioDomain)).thenReturn(tipoUsuarioEntity);
            when(repository.save(tipoUsuarioEntity)).thenReturn(tipoUsuarioEntity);
            when(mapper.toTipoUsuario(tipoUsuarioEntity)).thenReturn(tipoUsuarioDomain);

            TipoUsuario resultado = tipoUsuarioJpaGateway.criarTipoUsuario(tipoUsuarioDomain);

            assertNotNull(resultado);
            assertEquals(TIPO_USUARIO_ID, resultado.getId());

            verify(mapper, times(1)).toEntity(tipoUsuarioDomain);
            verify(repository, times(1)).save(tipoUsuarioEntity);
            verify(mapper, times(1)).toTipoUsuario(tipoUsuarioEntity);
        }

        @Test
        @DisplayName("Deve retornar Optional com TipoUsuario quando encontrado")
        void deveRetornarOptionalComTipoUsuario() {

            when(repository.findById(TIPO_USUARIO_ID)).thenReturn(Optional.of(tipoUsuarioEntity));

            when(mapper.toTipoUsuario(tipoUsuarioEntity)).thenReturn(tipoUsuarioDomain);

            Optional<TipoUsuario> resultado = tipoUsuarioJpaGateway.buscarPorId(TIPO_USUARIO_ID);

            assertTrue(resultado.isPresent());
            assertEquals(TIPO_USUARIO_ID, resultado.get().getId());
            verify(repository, times(1)).findById(TIPO_USUARIO_ID);
            verify(mapper, times(1)).toTipoUsuario(tipoUsuarioEntity);
        }

        @Test
        @DisplayName("Deve retornar Optional vazio quando não encontrado")
        void deveRetornarOptionalVazio() {

            when(repository.findById(TIPO_USUARIO_ID)).thenReturn(Optional.empty());

            Optional<TipoUsuario> resultado = tipoUsuarioJpaGateway.buscarPorId(TIPO_USUARIO_ID);

            assertFalse(resultado.isPresent());
            verify(repository, times(1)).findById(TIPO_USUARIO_ID);
            verifyNoInteractions(mapper);
        }

        @Test
        @DisplayName("Deve retornar lista de TiposUsuario e mapear todos")
        void deveRetornarListaBuscarPorTodosOsTiposUsuario() {
            TipoUsuarioEntity entity2 = new TipoUsuarioEntity();
            TipoUsuario domain2 = new TipoUsuario(2L, "CLIENTE", "CLIENTE DESCRICAO");

            List<TipoUsuarioEntity> entities = Arrays.asList(tipoUsuarioEntity, entity2);

            when(repository.findAll()).thenReturn(entities);
            when(mapper.toTipoUsuario(tipoUsuarioEntity)).thenReturn(tipoUsuarioDomain);
            when(mapper.toTipoUsuario(entity2)).thenReturn(domain2);

            List<TipoUsuario> resultados = tipoUsuarioJpaGateway.buscarTodosTiposUsuario();

            assertNotNull(resultados);
            assertEquals(2, resultados.size());
            assertEquals(TIPO_USUARIO_ID, resultados.get(0).getId());

            verify(repository, times(1)).findAll();
            verify(mapper, times(2)).toTipoUsuario(any(TipoUsuarioEntity.class));
        }

        @Test
        @DisplayName("Deve mapear, salvar a entidade e retornar o domínio (usando toDomain)")
        void deveMapearSalvarEConverter() {
            when(mapper.toEntity(tipoUsuarioDomain)).thenReturn(tipoUsuarioEntity);
            when(repository.save(tipoUsuarioEntity)).thenReturn(tipoUsuarioEntity);
            when(mapper.toDomain(tipoUsuarioEntity)).thenReturn(tipoUsuarioDomain);

            TipoUsuario resultado = tipoUsuarioJpaGateway.salvar(tipoUsuarioDomain);

            assertNotNull(resultado);
            assertEquals(TIPO_USUARIO_ID, resultado.getId());

            verify(mapper, times(1)).toEntity(tipoUsuarioDomain);
            verify(repository, times(1)).save(tipoUsuarioEntity);
            verify(mapper, times(1)).toDomain(tipoUsuarioEntity);
        }


        @Test
        @DisplayName("Deve mapear para entidade e chamar delete no Repository")
        void deveMapearEChamarDelete() {

            when(mapper.toEntity(tipoUsuarioDomain)).thenReturn(tipoUsuarioEntity);

            doNothing().when(repository).delete(tipoUsuarioEntity);

            tipoUsuarioJpaGateway.deletar(tipoUsuarioDomain);

            verify(mapper, times(1)).toEntity(tipoUsuarioDomain);
            verify(repository, times(1)).delete(tipoUsuarioEntity);
        }
    }