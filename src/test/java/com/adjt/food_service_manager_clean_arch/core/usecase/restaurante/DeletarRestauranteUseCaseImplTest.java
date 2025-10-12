package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletarRestauranteUseCaseImplTest {
    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private DeletarRestauranteUseCaseImpl deletarRestauranteUseCaseImpl;

    private final Long restauranteId = 1L;
    private final Long restauranteFake = 99L;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.builder()
                .id(restauranteId)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(new Usuario())
                .build();
    }

    @Test
    @DisplayName("Deve deletar o restaurante com sucesso quando encontrado")
    void deveDeletarQuandoRestauranteEncontrado() {

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restaurante));
        doNothing().when(restauranteGateway).deletar(restaurante);

        deletarRestauranteUseCaseImpl.deletarRestaurante(restauranteId);

        verify(restauranteGateway, times(1)).buscarPorId(restauranteId);
        verify(restauranteGateway, times(1)).deletar(restaurante);
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o restaurante não for encontrado")
    void deveLancarNotFoundQuandoRestauranteNaoEncontrado() {

        when(restauranteGateway.buscarPorId(restauranteFake)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarRestauranteUseCaseImpl.deletarRestaurante(restauranteFake);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Restaurante não encontrado.\"", exception.getMessage());

        verify(restauranteGateway, times(1)).buscarPorId(restauranteFake);
        verify(restauranteGateway, never()).deletar(any());
    }
}