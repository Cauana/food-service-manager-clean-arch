package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import org.springframework.web.bind.annotation.*;

import com.adjt.food_service_manager_clean_arch.core.dto.LoginRequestDto;
import com.adjt.food_service_manager_clean_arch.core.dto.LoginResponseDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.login.LoginUsuarioUseCase;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class LoginApiController {

    private final LoginUsuarioUseCase loginUsuarioUseCase;

    public LoginApiController(LoginUsuarioUseCase loginUsuarioUseCase) {
        this.loginUsuarioUseCase = loginUsuarioUseCase;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request, HttpSession session) {
        LoginResponseDto response = loginUsuarioUseCase.login(request);
        if (response.getMensagem().equals("Login realizado com sucesso!")) {
            session.setAttribute("usuario_logado", response);
            session.setAttribute("tipoUsuario", response.getTipoUsuario());
        }
        return response;
    }
}

