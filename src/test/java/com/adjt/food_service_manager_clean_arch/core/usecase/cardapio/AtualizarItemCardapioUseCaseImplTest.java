package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.gateway.ItemCardapioGateway;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioUseCaseImplTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;
    @InjectMocks
    private AtualizarItemCardapioUseCaseImpl atualizarItemCardapioUseCaseImpl;

    private HttpSession sessionMock;
    private CriarItemCardapioDto novosItemCardapioDto;
    private ItemCardapio itemCardapioExistente;

    private final Long itemCardpioId = 5L;
    private Restaurante restauranteMock;
    private Usuario usuarioDonoMock;

    @BeforeEach
    void setUp() {
        sessionMock = new MockHttpSession();
        novosItemCardapioDto = new CriarItemCardapioDto(
                "Iscas de Frango",
                "Pedaços de peito de frango com molho especial",
                34.9,
                true,
                "/imagens/cardapio/itens/iscas-frango.png",
                10L);

        itemCardapioExistente = new ItemCardapio(
                itemCardpioId,
                "Spaguete ao molho sugo",
                "Massa do tipo spaguete com molho sugo especial",
                44.9,
                true,
                "/imagens/cardapio/itens/spaguete.png",
                restauranteMock
        );


        restauranteMock = new Restaurante(10L ,
                "Spoleto",
                "Taguatinga",
                "Italiana",
                "10:00 as 22:00",
                usuarioDonoMock);

        usuarioDonoMock =  Usuario.builder()
                .id(1L)
                .nome("João da Silva")
                .cpf("40185812970")
                .tipoUsuario("DONO_RESTAURANTE")
                .build();
    }

//TODO: Corrigir esse teste
    /*
    @Test
    @DisplayName("Deve atualizar o item do carpadio com sucesso e retornar o objeto atualizado")
    void deveAtualizarItemCardapioComSucesso() {

        sessionMock.setAttribute("tipoUsuario", "DONO_RESTAURANTE");
        sessionMock.setAttribute("cpfUsuario", "40185812970");
        when(itemCardapioGateway.buscarPorId(itemCardpioId)).thenReturn(Optional.ofNullable(itemCardapioExistente));

        when(itemCardapioGateway.salvar(any(ItemCardapio.class))).thenReturn(itemCardapioExistente);

        ItemCardapio resultado = atualizarItemCardapioUseCaseImpl.atualizar(itemCardpioId, novosItemCardapioDto, sessionMock);

        assertNotNull(resultado);
        assertEquals(itemCardpioId, resultado.getId());

        assertEquals(novosItemCardapioDto.getNome(), resultado.getNome());
        assertEquals(novosItemCardapioDto.getDescricao(), resultado.getDescricao());
        assertEquals(novosItemCardapioDto.getPreco(), resultado.getPreco());

        verify(itemCardapioGateway, times(1)).buscarPorId(itemCardpioId);
        verify(itemCardapioGateway, times(1)).salvar(itemCardapioExistente);
    }*/

    @Test
    @DisplayName("Deve lançar ResponseStatusException 404 quando o item do carpadio não é encontrado")
    void deveLancarNotFoundQuandoItemCardapioNaoEncontrado() {

        Long idNaoExistente = 99L;
        when(itemCardapioGateway.buscarPorId(idNaoExistente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarItemCardapioUseCaseImpl.atualizar(idNaoExistente, novosItemCardapioDto, sessionMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("404 NOT_FOUND \"Item do cardápio não encontrado\"", exception.getMessage());

        verify(itemCardapioGateway, times(1)).buscarPorId(idNaoExistente);
        verify(itemCardapioGateway, never()).criarItemCardapio(any());
    }

}

