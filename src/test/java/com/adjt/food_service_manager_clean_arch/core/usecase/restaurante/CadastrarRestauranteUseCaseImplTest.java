package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.exception.ResourceNotFoundException;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteGateway restauranteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private CadastrarRestauranteUseCaseImpl cadastrarRestauranteUseCaseImpl;

    private Usuario usuarioMock;
    private Restaurante restauranteMock;
    private HttpSession sessionMock;

    private static final String DONO = "DONO_RESTAURANTE";
    private static final Long DONO_ID = 1001L;

    @BeforeEach
    void setUp() {
        sessionMock = mock(HttpSession.class);
        usuarioMock = Usuario.builder().id(DONO_ID).nome("Jose Carlos").cpf("18065259090").tipoUsuario(DONO).build();
        restauranteMock = Restaurante.builder()
                .id(DONO_ID)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(usuarioMock)
                .build();
    }

    @Test
    @DisplayName("Deve criar um novo restaurante")
    void deveCriarUmNovoRestaurante() {

        when(usuarioGateway.buscarPorId(usuarioMock.getId())).thenReturn(Optional.of(usuarioMock));
        when(sessionMock.getAttribute("tipoUsuario")).thenReturn(DONO);
        when(sessionMock.getAttribute("cpfUsuario")).thenReturn(usuarioMock.getCpf());
        when(restauranteGateway.criarRestaurante(any(Restaurante.class))).thenReturn(restauranteMock);

        var restauranteCriado = cadastrarRestauranteUseCaseImpl.criarRestaurante(restauranteDTO(), sessionMock );

        assertNotNull(restauranteCriado);
        assertEquals(restauranteMock.getNome(), restauranteCriado.getNome());
        assertEquals(Restaurante.class, restauranteCriado.getClass());

    }

    public CriarRestauranteDto restauranteDTO() {
        return CriarRestauranteDto.builder()
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .idDonoRestaurante(usuarioMock.getId())
                .build();
    }


}