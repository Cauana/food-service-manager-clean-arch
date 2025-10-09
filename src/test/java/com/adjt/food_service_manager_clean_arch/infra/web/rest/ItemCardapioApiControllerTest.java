package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.cardapio.BuscarItemCardapioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.cardapio.CadastrarItemCardapioUseCaseImpl;
import com.adjt.food_service_manager_clean_arch.core.usecase.cardapio.ListarTodosItensCardapioUseCaseImpl;
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
class ItemCardapioApiControllerTest {

    @Mock
    private  CadastrarItemCardapioUseCaseImpl cadastrarItemCardapioUseCaseImpl;

    @Mock
    private  BuscarItemCardapioUseCaseImpl buscarItemCardapioUseCaseImpl;

    @Mock
    private  ListarTodosItensCardapioUseCaseImpl listarTodosItensCardapioUseCaseImpl;

    @InjectMocks
    private ItemCardapioApiController itemCardapioApiController;

    @Mock
    private HttpSession sessionMock;

    private CriarItemCardapioDto criarItemCardapioDto;
    private ItemCardapio itemCardapioCriado;
    private ItemCardapio itemCardapioEncontrado;

    @BeforeEach
    void setup(){
        criarItemCardapioDto = new CriarItemCardapioDto("Iscas de Frango", "Pedaços de peito de frango com molho especial", 34.9, true, "/imagens/cardapio/itens/iscas-frango.png", 1001L);
        itemCardapioCriado = new ItemCardapio(101L , "Iscas de Frango", "Pedaços de peito de frango com molho especial", 34.9, true, "/imagens/cardapio/itens/iscas-frango.png", Restaurante.builder().build());
        itemCardapioEncontrado = new ItemCardapio(201L , "Spaguete ao molho sugo", "Massa do tipo spaguete com molho sugo especial", 44.9, true, "/imagens/cardapio/itens/spaguete.png", Restaurante.builder().build());
    }
    @Test
    @DisplayName("Deve criar um item do cardápio com sucesso e retornar 201 CREATED")
    void deveCriarItemCardapioRetornarCreatedQuandoSucesso(){
        when(cadastrarItemCardapioUseCaseImpl.criarItemCardapio(criarItemCardapioDto, sessionMock)).thenReturn(itemCardapioCriado);

        ResponseEntity<RespostaItemCardapioDto> response =  itemCardapioApiController.criarItemCardapio(criarItemCardapioDto, sessionMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(itemCardapioCriado.getId(), response.getBody().getId());
        assertEquals(itemCardapioCriado.getNome(), response.getBody().getNome());
        verify(cadastrarItemCardapioUseCaseImpl, times(1)).criarItemCardapio(criarItemCardapioDto, sessionMock);
    }

    @Test
    @DisplayName("Deve buscar um item do cardápio por ID com sucesso e retornar 200 OK")
    void deveBuscarItemCardapioPorIdRetornarOkQuandoItemCardapioEncontrado() {
        Long itemCardapioId = 2L;

        when(buscarItemCardapioUseCaseImpl.buscarItemCardapio(itemCardapioId)).thenReturn(itemCardapioEncontrado);
        ResponseEntity<RespostaItemCardapioDto> response = itemCardapioApiController.buscarPorId(itemCardapioId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(itemCardapioEncontrado.getId(), response.getBody().getId());
        assertEquals(itemCardapioEncontrado.getNome(), response.getBody().getNome());
        verify(buscarItemCardapioUseCaseImpl, times(1)).buscarItemCardapio(itemCardapioId);

    }

    @Test
    @DisplayName("Deve listar todos os itens do cardápio e retornar 200 OK")
    void deveListarTodosItensCardapioRetornarOkComListaDeItensCardapio(){
        List<ItemCardapio> itensCardapio = Arrays.asList(itemCardapioCriado, itemCardapioEncontrado);
        when(listarTodosItensCardapioUseCaseImpl.listarTodosItemCardapioUseCase()).thenReturn(itensCardapio);

        ResponseEntity<List<RespostaItemCardapioDto>> response =  itemCardapioApiController.listarItensCardapio();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(itemCardapioCriado.getNome(), response.getBody().getFirst().getNome());
        assertEquals(itemCardapioEncontrado.getNome(), response.getBody().getLast().getNome());
        verify(listarTodosItensCardapioUseCaseImpl, times(1)).listarTodosItemCardapioUseCase();
    }

    @Test
    @DisplayName("Deve restornar uma lista vazia quando nenhum item do cardápio for encontrado")
    void deveRetornarListaVaziaQuandoNenhumItemCardapioForEncontrado(){
        List<ItemCardapio> itensCardapio = List.of();
        when(listarTodosItensCardapioUseCaseImpl.listarTodosItemCardapioUseCase()).thenReturn(itensCardapio);

        ResponseEntity<List<RespostaItemCardapioDto>> response =  itemCardapioApiController.listarItensCardapio();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());

    }

}