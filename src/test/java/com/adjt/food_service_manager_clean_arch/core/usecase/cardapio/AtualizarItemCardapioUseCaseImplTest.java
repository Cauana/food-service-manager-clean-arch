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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizarItemCardapioUseCaseImplTest {

    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private AtualizarItemCardapioUseCaseImpl atualizarItemCardapioUseCase;

    private final Long itemCardapioId = 5L;
    private final Long inexistenteId = 99L;
    private final String cpfDono = "12345678900";
    private final String cpfOutro = "98765432100";
    private final String tipoDono = "DONO_RESTAURANTE";
    private final String tipoCliente = "CLIENTE";

    private HttpSession sessionMock;
    private ItemCardapio itemExistente;
    private CriarItemCardapioDto dtoAtualizacao;

    @BeforeEach
    void setUp() {
        sessionMock = new MockHttpSession();

        Usuario dono = new Usuario(1L, "Henrique Fogaça", "fogacaa@rest.com", cpfDono, "fogaca01", "senha123", tipoDono, Collections.singletonList(Restaurante.builder().build()));

        Restaurante restaurante = new Restaurante(10L, "Sal Gastronomia", "Cidade Jardim", "Contemporânea", "11h-23h", dono);

        itemExistente = new ItemCardapio(
                itemCardapioId,
                "Pizza Antiga",
                "Massa fina",
                50.00,
                true,
                "foto_antiga.jpg",
                restaurante
        );

        dtoAtualizacao = new CriarItemCardapioDto(
                "Pizza Nova",
                "Massa grossa, mais recheio",
                65.00,
                false,
                "foto_nova.jpg",
                null
        );
    }

    @Test
    @DisplayName("Deve atualizar o item do cardápio com sucesso quando o usuário for o dono correto")
    void deveAtualizarComSucessoQuandoUsuarioEODono() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDono);

        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemExistente));
        when(itemCardapioGateway.salvar(any(ItemCardapio.class))).thenReturn(itemExistente);

        ItemCardapio resultado = atualizarItemCardapioUseCase.atualizar(itemCardapioId, dtoAtualizacao, sessionMock);

        assertNotNull(resultado);
        assertEquals(dtoAtualizacao.getNome(), resultado.getNome());
        assertEquals(dtoAtualizacao.getPreco(), resultado.getPreco());
        assertEquals(dtoAtualizacao.getDescricao(), resultado.getDescricao());

        verify(itemCardapioGateway, times(1)).buscarPorId(itemCardapioId);
        verify(itemCardapioGateway, times(1)).salvar(itemExistente);
    }

    @Test
    @DisplayName("Deve lançar 404 NOT FOUND se o item do cardápio não for encontrado")
    void deveLancarErroQuandoItemCardapioNaoEncontrado() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(inexistenteId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarItemCardapioUseCase.atualizar(inexistenteId, dtoAtualizacao, sessionMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Item do cardápio não encontrado"));

        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o tipo de usuário não for DONO_RESTAURANTE")
    void deveLancarErrroSeTipoUsuarioIncorreto() {
        sessionMock.setAttribute("tipoUsuario", tipoCliente);
        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemExistente));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarItemCardapioUseCase.atualizar(itemCardapioId, dtoAtualizacao, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Somente o dono do restaurante pode atualizar"));

        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o usuário for DONO_RESTAURANTE, mas o CPF for diferente")
    void deveFalharNaAutorizacaoDonoIncorreto() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfOutro);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemExistente));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarItemCardapioUseCase.atualizar(itemCardapioId, dtoAtualizacao, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Somente o dono do restaurante pode atualizar"));

        verify(itemCardapioGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se tipoUsuario for nulo na sessão")
    void deveFalharNaAutorizacaoTipoUsuarioNulo() {
        sessionMock.setAttribute("tipoUsuario", null);
        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemExistente));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            atualizarItemCardapioUseCase.atualizar(itemCardapioId, dtoAtualizacao, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Somente o dono do restaurante pode atualizar"));
    }
}