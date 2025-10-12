package com.adjt.food_service_manager_clean_arch.core.usecase.restaurante;

import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarRestauranteDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.UsuarioGateway;
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

@ExtendWith(MockitoExtension.class)
class AtualizarRestauranteUseCaseImplTest {


    @Mock
    private RestauranteGateway restauranteGateway;
    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private AtualizarRestauranteUseCaseImpl atualizarRestauranteUseCaseImpl;

    private final Long restauranteId = 1001L;
    private final Long donoAntigoId = 10L;
    private final Long donoNovoId = 20L;
    private CriarRestauranteDto novoDadosRestauranteDto;
    private Restaurante restauranteExistente;
    private Usuario donoAntigo;
    private Usuario donoNovo;

    @BeforeEach
    void setUp() {
        novoDadosRestauranteDto = CriarRestauranteDto.builder()
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .idDonoRestaurante(donoNovoId)
                .build();

        restauranteExistente = Restaurante.builder()
                .id(1001L)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(donoAntigo)
                .build();

        donoAntigo = Usuario.builder()
                .id(donoAntigoId)
                .nome("Maria Jose")
                .tipoUsuario("DONO_RESTAURANTE")
                .build();

        donoNovo = Usuario.builder()
                .id(donoNovoId)
                .nome("João da Silva")
                .tipoUsuario("DONO_RESTAURANTE")
                .build();
    }

    @Test
    @DisplayName("Deve atualizar o restaurante com sucesso e retornar o objeto atualizado")
    void deveAtualizarRestauranteComSucesso() {

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restauranteExistente));
        when(usuarioGateway.buscarPorId(donoNovoId)).thenReturn(Optional.ofNullable(donoNovo));
        when(restauranteGateway.salvar(any(Restaurante.class))).thenReturn(restauranteExistente);

        Restaurante resultado = atualizarRestauranteUseCaseImpl.atualizarRestaurante(restauranteId, novoDadosRestauranteDto);

        assertNotNull(resultado);
        assertNotNull(resultado.getDonoRestaurante());
        assertEquals(restauranteId, resultado.getId());
        assertEquals(novoDadosRestauranteDto.getNome(), resultado.getNome());
        assertEquals(novoDadosRestauranteDto.getTipoCozinha(), resultado.getTipoCozinha());
        assertEquals(novoDadosRestauranteDto.getEndereco(), resultado.getEndereco());

        verify(restauranteGateway, times(1)).buscarPorId(restauranteId);
        verify(restauranteGateway, times(1)).salvar(restauranteExistente);
        verify(usuarioGateway, times(1)).buscarPorId(donoNovoId);

        assertEquals(novoDadosRestauranteDto.getNome(), restauranteExistente.getNome());
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o restaurante não for encontrado")
    void deveLancarNotFoundQuandoRestauranteNaoEncontrado() {

        Long idNaoExistente = 99L;
        when(restauranteGateway.buscarPorId(idNaoExistente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarRestauranteUseCaseImpl.atualizarRestaurante(idNaoExistente, novoDadosRestauranteDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Restaurante não encontrado\"", exception.getMessage());

        verify(restauranteGateway, times(1)).buscarPorId(idNaoExistente);
        verify(restauranteGateway, never()).criarRestaurante(any());
    }
}