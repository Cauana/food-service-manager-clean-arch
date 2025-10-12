package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.login.LoginUsuarioUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class LoginApiControllerTest {

    @Mock
    private LoginUsuarioUseCase loginUsuarioUseCase;

    @InjectMocks
    private LoginApiController loginApiController;


    private HttpSession sessionMock;
    private LoginRequestDto requisicaoValida;
    private LoginRequestDto requisicaoInvalida;
    private LoginResponseDto respostaValida;
    private LoginResponseDto respostaInvalida;

    @BeforeEach
    void setUp() {
        sessionMock = new MockHttpSession();
        requisicaoValida = new LoginRequestDto("Joao", "senha123");
        requisicaoInvalida = new LoginRequestDto("loginErrado", "senhaErrada");
        respostaValida = new LoginResponseDto("Login realizado com sucesso!", "0010020034", "João", "CLIENTE", List.of());
        respostaInvalida = new LoginResponseDto("Erro no login", null, null, null, null);
    }


    @Test
    @DisplayName("Deve realizar o login com sucesso")
    void deveRealizarLoginComSucesso() {

        when(loginUsuarioUseCase.login(requisicaoValida)).thenReturn(respostaValida);

        LoginResponseDto response = loginApiController.login(requisicaoValida, sessionMock);

        assertNotNull(response);
        assertEquals("Login realizado com sucesso!", response.getMensagem());

        verify(loginUsuarioUseCase, times(1)).login(requisicaoValida);

        assertEquals(respostaValida, sessionMock.getAttribute("usuario_logado"));
        assertEquals("CLIENTE", sessionMock.getAttribute("tipoUsuario"));
        assertEquals("0010020034", sessionMock.getAttribute("cpfUsuario"));
    }

    @Test
    @DisplayName("Deve retornar falha de login")
    void deveFalharNoLogin() {
        when(loginUsuarioUseCase.login(requisicaoInvalida)).thenReturn(respostaInvalida);

        LoginResponseDto response = loginApiController.login(requisicaoInvalida, sessionMock);

        assertNotNull(response);
        assertEquals("Erro no login", response.getMensagem());

        verify(loginUsuarioUseCase, times(1)).login(requisicaoInvalida);

        assertNull(sessionMock.getAttribute("usuario_logado"));
        assertNull(sessionMock.getAttribute("tipoUsuario"));
        assertNull(sessionMock.getAttribute("cpfUsuario"));
    }


    @Test
    @DisplayName("Deve invalidar a sessão ao realizar o logout")
    void deveInvalidarSessao() {

        sessionMock.setAttribute("usuario_logado", "usuario_mock");

        HttpSession mockSession = mock(HttpSession.class);

        loginApiController.logout(mockSession);

        verify(mockSession, times(1)).invalidate();

    }
}
