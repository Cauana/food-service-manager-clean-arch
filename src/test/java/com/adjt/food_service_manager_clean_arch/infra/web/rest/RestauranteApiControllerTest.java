package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.BuscarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.CadastrarRestauranteUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.restaurante.ListarTodosRestaurantesUseCaseImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RestauranteApiControllerTest {

    @Mock
    private CadastrarRestauranteUseCaseImpl cadastrarRestauranteUseCaseImpl;

    @Mock
    private BuscarRestauranteUseCaseImpl buscarRestauranteUseCaseImpl;

    @Mock
    private ListarTodosRestaurantesUseCaseImpl listarTodosRestaurantesUseCaseImpl;
    @InjectMocks
    private RestauranteApiController restauranteApiController;

    @Mock
    private HttpSession sessionMock;

    private CriarRestauranteDto criarRestauranteDto;
    private Restaurante restauranteCriado;
    private Restaurante restauranteEncontrado;

    @BeforeEach
    void setUp() {
        criarRestauranteDto = new CriarRestauranteDto("Giraffas", "Brasilia", "Brasileira", "10:00 as 23:00", 101L);
        restauranteCriado = new Restaurante(1L ,"Giraffas", "Samambaia", "Brasileira", "10:00 as 23:00", new Usuario());
        restauranteEncontrado = new Restaurante(2L ,"Spoleto", "Taguatinga", "Italiana", "10:00 as 22:00", new Usuario());
    }

    @Test
    @DisplayName("Deve criar um restaurante com sucesso e retornar 201 CREATED")
    void deveCriarRestauranteRetornarCreatedQuandoSucesso(){
        when(cadastrarRestauranteUseCaseImpl.criarRestaurante(criarRestauranteDto, sessionMock )).thenReturn(restauranteCriado);

        ResponseEntity<RespostaRestauranteDto> response =  restauranteApiController.criarRestaurante(criarRestauranteDto, sessionMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restauranteCriado.getId(), response.getBody().getId());
        assertEquals(restauranteCriado.getNome(), response.getBody().getNome());
        verify(cadastrarRestauranteUseCaseImpl, times(1)).criarRestaurante(criarRestauranteDto, sessionMock);
    }

    @Test
    @DisplayName("Deve buscar um restaurante por ID com sucesso e retornar 200 OK")
    void deveBuscarRestaurantePorIdRetornarOkQuandoRestauranteEncontrado() {
        Long restauranteId = 2L;

        when(buscarRestauranteUseCaseImpl.buscarRestaurante(restauranteId)).thenReturn(restauranteEncontrado);
        ResponseEntity<RespostaRestauranteDto> response = restauranteApiController.buscarPorId(restauranteId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(restauranteEncontrado.getId(), response.getBody().getId());
        assertEquals(restauranteEncontrado.getNome(), response.getBody().getNome());
        verify(buscarRestauranteUseCaseImpl, times(1)).buscarRestaurante(restauranteId);

    }

    @Test
    @DisplayName("Deve listar todos os restaurantes e retornar 200 OK")
    void deveListarTodosRestaurantesRetornarOkComListaDeRestaurantes(){
        List<Restaurante> restaurantes = Arrays.asList(restauranteCriado, restauranteEncontrado);
        when(listarTodosRestaurantesUseCaseImpl.listarTodosRestaurantes()).thenReturn(restaurantes);

        ResponseEntity<List<RespostaRestauranteDto>> response =  restauranteApiController.listarRestaurantes();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(restauranteCriado.getNome(), response.getBody().getFirst().getNome());
        assertEquals(restauranteEncontrado.getNome(), response.getBody().getLast().getNome());
        verify(listarTodosRestaurantesUseCaseImpl, times(1)).listarTodosRestaurantes();
    }

    @Test
    @DisplayName("Deve restornar uma lista vazia quando nenhum restaurante for encontrado")
    void deveRetornarListaVaziaQuandoNenhumRestauranteForEncontrado(){
        List<Restaurante> restaurantes = List.of();
        when(listarTodosRestaurantesUseCaseImpl.listarTodosRestaurantes()).thenReturn(restaurantes);

        ResponseEntity<List<RespostaRestauranteDto>> response =  restauranteApiController.listarRestaurantes();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

}