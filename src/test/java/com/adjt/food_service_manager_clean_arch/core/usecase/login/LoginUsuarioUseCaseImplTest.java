package com.adjt.food_service_manager_clean_arch.core.usecase.login;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    class LoginUsuarioUseCaseImplTest {


        @Mock
        private UsuarioGateway usuarioGateway;

        @InjectMocks
        private LoginUsuarioUseCaseImpl loginUsuarioUseCase;

        private final String LOGIN_CORRETO = "admin";
        private final String SENHA_CORRETA = "senha123";
        private final String SENHA_INCORRETA = "senhaerrada";
        private final String CPF_USUARIO = "11122233344";

        private LoginRequestDto requestSucesso;
        private LoginRequestDto requestFalhaSenha;
        private Usuario usuarioEncontrado;
        private List<Long> restaurantesIdsEsperados;

        @BeforeEach
        void setUp() {
            Restaurante restaurante1 = new Restaurante(10L, "LUCKY", "Conselheiro Furtado", "Oriental", "10:00 as 23:00", null);
            Restaurante restaurante2 = new Restaurante(11L, "YAKISSOBA LIBERDADE", "Tamandaré", "Oriental", "11:00 as 23:00", null);
            List<Restaurante> restaurantes = List.of(restaurante1, restaurante2);

            restaurantesIdsEsperados = List.of(10L, 11L);

            usuarioEncontrado = new Usuario(
                    5L,
                    "Admin",
                    "admin@login.com",
                    CPF_USUARIO,
                    LOGIN_CORRETO,
                    SENHA_CORRETA,
                    "CLIENTE",
                    restaurantes
            );

            requestSucesso = new LoginRequestDto(LOGIN_CORRETO, SENHA_CORRETA);
            requestFalhaSenha = new LoginRequestDto(LOGIN_CORRETO, SENHA_INCORRETA);
        }

        @Test
        @DisplayName("Deve realizar o login com sucesso e retornar o DTO completo")
        void deveRetornarSucessoQuandoCredenciaisCorretas() {
            when(usuarioGateway.buscarPorLogin(LOGIN_CORRETO)).thenReturn(Optional.of(usuarioEncontrado));

            LoginResponseDto response = loginUsuarioUseCase.login(requestSucesso);

            assertNotNull(response);
            assertEquals("Login realizado com sucesso!", response.getMensagem());
            assertEquals(CPF_USUARIO, response.getCpf());
            assertEquals(usuarioEncontrado.getNome(), response.getNome());
            assertEquals(usuarioEncontrado.getTipoUsuario(), response.getTipoUsuario());

            assertNotNull(response.getRestaurantesIds());
            assertEquals(restaurantesIdsEsperados, response.getRestaurantesIds());

            verify(usuarioGateway, times(1)).buscarPorLogin(LOGIN_CORRETO);
        }

        @Test
        @DisplayName("Deve lançar RuntimeException se o usuário não for encontrado")
        void deveLancarRuntimeExceptionQuandoUsuarioNaoEncontrado() {

            when(usuarioGateway.buscarPorLogin(anyString())).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                loginUsuarioUseCase.login(requestSucesso);
            });

            assertEquals("Usuário não encontrado", exception.getMessage());
            verify(usuarioGateway, times(1)).buscarPorLogin(requestSucesso.getLogin());
        }

        @Test
        @DisplayName("Deve retornar DTO com mensagem de erro quando a senha está incorreta")
        void deveRetornarFalhaQuandoSenhaIncorreta() {

            when(usuarioGateway.buscarPorLogin(LOGIN_CORRETO)).thenReturn(Optional.of(usuarioEncontrado));

            LoginResponseDto response = loginUsuarioUseCase.login(requestFalhaSenha);

            assertNotNull(response);
            assertEquals("Senha incorreta", response.getMensagem());

            assertNull(response.getCpf());
            assertNull(response.getNome());
            assertNull(response.getTipoUsuario());
            assertNull(response.getRestaurantesIds());

            verify(usuarioGateway, times(1)).buscarPorLogin(LOGIN_CORRETO);
        }
    }