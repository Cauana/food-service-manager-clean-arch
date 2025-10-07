package com.adjt.food_service_manager_clean_arch.core.usecase.usuario;

import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import org.hibernate.type.descriptor.sql.internal.Scale6IntervalSecondDdlType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private BuscarUsuarioUseCaseImpl buscarUsuarioUseCaseImpl;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = Usuario.builder()
                .id(1001L)
                .tipoUsuario("DONO_RESTAURANTE")
                .nome("Maria Silva")
                .email("\"maria.silva@teste.com")
                .login("mariasilva")
                .cpf("123.456.789-00")
                .restaurantes(Collections.emptyList())
                .build();
    }

    @Test
    void deveriaBuscarUsuarioUseCaseImpl() {
        Long id = 1001L;

        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.of(usuarioMock));

        Usuario usuarioEncontrado = buscarUsuarioUseCaseImpl.buscarUsuario(id);

        assertNotNull(usuarioEncontrado);
        assertEquals(id, usuarioEncontrado.getId());
        assertEquals(usuarioMock.getNome(), usuarioEncontrado.getNome());

        verify(usuarioGateway, times(1)).buscarPorId(id);
    }


    void deveLancarExcecaoUsuarioNaoEncontrado() {
        Long id = 1002L;
        when(usuarioGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ResourceNotFoundException  exception = assertThrows(ResourceNotFoundException.class,() -> buscarUsuarioUseCaseImpl.buscarUsuario(id));

        assertEquals("Usuario Não Encontrado", exception.getMessage());

        verify(usuarioGateway, times(1)).buscarPorId(id);
    }




}