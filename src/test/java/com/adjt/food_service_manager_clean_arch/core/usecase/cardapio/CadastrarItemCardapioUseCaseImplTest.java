package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
import com.adjt.food_service_manager_clean_arch.core.gateway.RestauranteGateway;
import com.adjt.food_service_manager_clean_arch.core.usecase.tipousuario.TipoUsuarioUseCaseImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarItemCardapioUseCaseImplTest {
    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @Mock
    private RestauranteGateway restauranteGateway;

    @InjectMocks
    private CadastrarItemCardapioUseCaseImpl cadastrarItemCardapioUseCaseImpl;

    private final Long restauranteId = 1L;
    private final String cpfDonoCorreto = "12345678900";
    private final String cpfDonoIncorreto = "98765432100";
    private final String tipoDono = "DONO_RESTAURANTE";

    private Restaurante restauranteCorreto;
    private CriarItemCardapioDto criarItemCardapioDto;
    private ItemCardapio itemCardapioCriado;

    @Mock
    private HttpSession sessionMock;

    @BeforeEach
    void setUp() {
        sessionMock = new MockHttpSession();

        Usuario dono = Usuario.builder()
                .id(10L)
                .nome("João da Silva")
                .email("joao.silva@teste.com")
                .cpf("12345678900")
                .login("joao.login")
                .senha("senha123")
                .tipoUsuario("CLIENTE")
                .restaurantes(Collections.emptyList())
                .build();


        restauranteCorreto = Restaurante.builder()
                .id(restauranteId)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(dono)
                .build();

        criarItemCardapioDto = CriarItemCardapioDto.builder()
                .nome("Iscas de Frango")
                .descricao("Pedaços de peito de frango com molho especial")
                .preco(34.9)
                .disponibilidade(true)
                .foto("/imagens/cardapio/itens/iscas-frango.png")
                .idRestaurante(1L)
                .build();

        itemCardapioCriado = ItemCardapio.builder()
                .id(100L)
                .nome("Spaguete ao molho sugo")
                .descricao("Massa do tipo spaguete com molho sugo especial")
                .preco(44.9)
                .disponibilidade(true)
                .foto("/imagens/cardapio/itens/spaguete.png")
                .restaurante(restauranteCorreto)
                .build();
    }

    @Test
    @DisplayName("Deve criar um novo item do cardápio")
    void deveCriarUmNovoItemCardapio() {

        sessionMock.setAttribute("tipoUsuario", "DONO_RESTAURANTE");
        sessionMock.setAttribute("cpfUsuario", cpfDonoCorreto);

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.ofNullable(restauranteCorreto));

        when(itemCardapioGateway.criarItemCardapio(any(ItemCardapio.class))).thenReturn(itemCardapioCriado);

        ItemCardapio resultado = cadastrarItemCardapioUseCaseImpl.criarItemCardapio(criarItemCardapioDto, sessionMock);

        assertNotNull(resultado);
        assertEquals(itemCardapioCriado.getNome(), resultado.getNome());
        assertEquals(restauranteCorreto, resultado.getRestaurante());
        assertEquals(ItemCardapio.class, resultado.getClass());
    }

    @Test
    @DisplayName("Deve lançar 404 NOT FOUND se o Restaurante referenciado não for encontrado")
    void deveFalharQuandoRestauranteNaoEncontrado() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDonoCorreto);

        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cadastrarItemCardapioUseCaseImpl.criarItemCardapio(criarItemCardapioDto, sessionMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Restaurante não encontrado\"", exception.getMessage());

        verifyNoInteractions(itemCardapioGateway);
    }



    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o CPF logado não for o dono do restaurante")
    void deveFalharComDonoIncorreto() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDonoIncorreto);
        when(restauranteGateway.buscarPorId(restauranteId)).thenReturn(Optional.of(restauranteCorreto));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cadastrarItemCardapioUseCaseImpl.criarItemCardapio(criarItemCardapioDto, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Usuário logado não corresponde ao dono do restaurante"));

        verifyNoInteractions(itemCardapioGateway);
    }
}
