package com.adjt.food_service_manager_clean_arch.infra.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.UsuarioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.UsuarioRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.UsuarioEntityMapper;
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
class UsuarioJpaGatewayTest {
        @Mock
        private UsuarioRepository usuarioRepository;

        @Mock
        private UsuarioEntityMapper usuarioEntityMapper;

        @InjectMocks
        private UsuarioJpaGateway usuarioJpaGateway;

        private final Long USUARIO_ID = 1L;
        private final String LOGIN = "test_user";
        private final String CPF = "12345678900";
        private Usuario usuarioDomain;
        private UsuarioEntity usuarioEntity;

        @BeforeEach
        void setUp() {
            usuarioDomain = new Usuario(
                    USUARIO_ID,
                    "Nome Teste",
                    "teste@email.com",
                    CPF,
                    LOGIN,
                    "senha",
                    "CLIENTE",
                    null
            );

            usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(USUARIO_ID);
            usuarioEntity.setLogin(LOGIN);
            usuarioEntity.setCpf(CPF);
        }

        @Test
        @DisplayName("Deve mapear, salvar a entidade e retornar o domínio")
        void deveSalvarEConverterComSucesso() {

            when(usuarioEntityMapper.toEntity(usuarioDomain)).thenReturn(usuarioEntity);

            when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);

            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);

            Usuario resultado = usuarioJpaGateway.criar(usuarioDomain);

            assertNotNull(resultado);
            assertEquals(USUARIO_ID, resultado.getId());

            verify(usuarioEntityMapper, times(1)).toEntity(usuarioDomain);
            verify(usuarioRepository, times(1)).save(usuarioEntity);
            verify(usuarioEntityMapper, times(1)).toUsuario(usuarioEntity);
        }

        @Test
        @DisplayName("Deve lançar RuntimeException em caso de falha no Repository")
        void deveLancarRuntimeExceptionEmCasoDeErro() {

            when(usuarioEntityMapper.toEntity(usuarioDomain)).thenReturn(usuarioEntity);

            when(usuarioRepository.save(usuarioEntity)).thenThrow(new RuntimeException("DB error"));

            RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
                usuarioJpaGateway.criar(usuarioDomain);
            });

            assertTrue(thrown.getMessage().contains("Erro ao salvar usuário"));
            verify(usuarioRepository, times(1)).save(usuarioEntity);
        }

        @Test
        @DisplayName("Deve chamar o método 'criar' (delegando a mesma lógica)")
        void deveDelegarParaCriar() {

            when(usuarioEntityMapper.toEntity(usuarioDomain)).thenReturn(usuarioEntity);
            when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);
            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);

            Usuario resultado = usuarioJpaGateway.salvar(usuarioDomain);

            assertNotNull(resultado);

            verify(usuarioEntityMapper, times(1)).toEntity(usuarioDomain);
            verify(usuarioRepository, times(1)).save(usuarioEntity);
        }

        @Test
        @DisplayName("Deve retornar Optional com Usuario quando encontrado")
        void deveRetornarOptionalComUsuario() {

            when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.of(usuarioEntity));
            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);

            Optional<Usuario> resultado = usuarioJpaGateway.buscarPorId(USUARIO_ID);

            assertTrue(resultado.isPresent());
            assertEquals(USUARIO_ID, resultado.get().getId());
            verify(usuarioRepository, times(1)).findById(USUARIO_ID);
            verify(usuarioEntityMapper, times(1)).toUsuario(usuarioEntity);
        }

        @Test
        @DisplayName("Deve retornar Optional vazio quando não encontrado")
        void deveRetornarOptionalVazioBuscaPorId() {

            when(usuarioRepository.findById(USUARIO_ID)).thenReturn(Optional.empty());

            Optional<Usuario> resultado = usuarioJpaGateway.buscarPorId(USUARIO_ID);

            assertFalse(resultado.isPresent());
            verify(usuarioRepository, times(1)).findById(USUARIO_ID);

            verifyNoInteractions(usuarioEntityMapper);
        }

        @Test
        @DisplayName("Deve retornar Optional com Usuario quando encontrado")
        void deveRetornarOptionalComUsuarioNaBuscaPorLogin() {

            when(usuarioRepository.findByLogin(LOGIN)).thenReturn(Optional.of(usuarioEntity));
            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);

            Optional<Usuario> resultado = usuarioJpaGateway.buscarPorLogin(LOGIN);

            assertTrue(resultado.isPresent());
            assertEquals(LOGIN, resultado.get().getLogin());
            verify(usuarioRepository, times(1)).findByLogin(LOGIN);
            verify(usuarioEntityMapper, times(1)).toUsuario(usuarioEntity);
        }

        @Test
        @DisplayName("Deve retornar lista de Usuarios e mapear todos")
        void deveRetornarListaDeUsuarios() {

            UsuarioEntity entity2 = new UsuarioEntity();
            Usuario domain2 = new Usuario(2L, "Teste 2", "t2@e.com", "222", "login2", "s","CLIENTE", null);

            List<UsuarioEntity> entities = Arrays.asList(usuarioEntity, entity2);

            when(usuarioRepository.findAll()).thenReturn(entities);
            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);
            when(usuarioEntityMapper.toUsuario(entity2)).thenReturn(domain2);

            List<Usuario> resultados = usuarioJpaGateway.buscarTodosUsuarios();

            assertNotNull(resultados);
            assertEquals(2, resultados.size());
            assertEquals(USUARIO_ID, resultados.get(0).getId());

            verify(usuarioRepository, times(1)).findAll();
            verify(usuarioEntityMapper, times(2)).toUsuario(any(UsuarioEntity.class));
        }

        @Test
        @DisplayName("Deve retornar Optional com Usuario quando encontrado (usando map)")
        void deveRetornarUsuarioQuandoEncontradoPorCPF() {

            when(usuarioRepository.findByCpf(CPF)).thenReturn(Optional.of(usuarioEntity));
            when(usuarioEntityMapper.toUsuario(usuarioEntity)).thenReturn(usuarioDomain);

            Optional<Usuario> resultado = usuarioJpaGateway.buscarPorCpf(CPF);

            assertTrue(resultado.isPresent());
            assertEquals(CPF, resultado.get().getCpf());
            verify(usuarioRepository, times(1)).findByCpf(CPF);
            verify(usuarioEntityMapper, times(1)).toUsuario(usuarioEntity);
        }

        @Test
        @DisplayName("Deve mapear para entidade e chamar delete no Repository")
        void deveMapearEChamarDelete() {

            when(usuarioEntityMapper.toEntity(usuarioDomain)).thenReturn(usuarioEntity);

            doNothing().when(usuarioRepository).delete(usuarioEntity);

            usuarioJpaGateway.deletar(usuarioDomain);

            verify(usuarioEntityMapper, times(1)).toEntity(usuarioDomain);
            verify(usuarioRepository, times(1)).delete(usuarioEntity);
        }
}