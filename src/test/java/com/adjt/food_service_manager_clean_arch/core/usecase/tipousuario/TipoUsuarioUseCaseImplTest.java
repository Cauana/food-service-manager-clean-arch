package com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario;

import com.adjt.food_service_manager_clean_arch.core.domain.TipoUsuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarTipoUsuarioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.TipoUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioUseCaseImplTest {
    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @InjectMocks
    private TipoUsuarioUseCaseImpl cadastrarTipoUsuarioUseCaseImpl;

    private TipoUsuario tipoUsuarioMock;
    private CriarTipoUsuarioDto criarTipoUsuarioDto;

    @BeforeEach
    void setUp() {
        tipoUsuarioMock = TipoUsuario.builder()
                .nome("DONO_RESTAURANTE")
                .descricao("Usuário tipo Dono")
                .build();

        criarTipoUsuarioDto = CriarTipoUsuarioDto.builder()
                .nome("CLIENTE")
                .descricao("Usuario tipo Cliente")
                .build();
    }

    @Test
    @DisplayName("Deve criar um novo tipo usuario")
    void deveCriarUmNovoUsuario() {

        when(tipoUsuarioGateway.criarTipoUsuario(any(TipoUsuario.class))).thenReturn(tipoUsuarioMock);

        var tipoUsuarioCriado = cadastrarTipoUsuarioUseCaseImpl.criarTipoUsuario(criarTipoUsuarioDto);

        assertNotNull(tipoUsuarioCriado);
        assertEquals(tipoUsuarioMock.getNome(), tipoUsuarioCriado.getNome());
        assertEquals(TipoUsuario.class, tipoUsuarioCriado.getClass());

    }

}