package com.adjt.food_service_manager_clean_arch.core.usecase.cardapio;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.domain.Usuario;
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
class DeletarItemCardapioUseCaseImplTest {
    @Mock
    private ItemCardapioGateway itemCardapioGateway;

    @InjectMocks
    private DeletarItemCardapioUseCaseImpl deletarItemCardapioUseCase;

    private final Long itemCardapioId = 1L;
    private final Long idInexistente = 99L;
    private final String cpfDono = "12345678900";
    private final String cpfOutroDono = "98765432100";
    private final String tipoDono = "DONO_RESTAURANTE";
    private final String tipoCliente = "CLIENTE";

    private HttpSession sessionMock;
    private ItemCardapio itemCardapio;

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
                .tipoUsuario(tipoCliente)
                .restaurantes(Collections.emptyList())
                .build();


        Restaurante restaurante = Restaurante.builder()
                .id(20L)
                .nome("Yakisoba Liberdade")
                .endereco("Liberdade Sao paulo")
                .tipoCozinha("Oriental")
                .horarioFuncionamento("10:00 as 23:00")
                .donoRestaurante(dono)
                .build();

        itemCardapio = ItemCardapio.builder()
                .id(itemCardapioId)
                .nome("Spaguete ao molho sugo")
                .descricao("Massa do tipo spaguete com molho sugo especial")
                .preco(44.9)
                .disponibilidade(true)
                .foto("/imagens/cardapio/itens/spaguete.png")
                .restaurante(restaurante)
                .build();
    }

    @Test
    @DisplayName("Deve deletar o item com sucesso quando o usuário for o dono correto")
    void deveDeletarComSucessoQuandoUsuarioEODonoCorreto() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDono);

        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.ofNullable(itemCardapio));
        doNothing().when(itemCardapioGateway).deletar(itemCardapio);

        deletarItemCardapioUseCase.deletar(itemCardapioId, sessionMock);

        verify(itemCardapioGateway, times(1)).buscarPorId(itemCardapioId);
        verify(itemCardapioGateway, times(1)).deletar(itemCardapio);
    }


    @Test
    @DisplayName("Deve lançar 404 NOT FOUND se o item do cardápio não for encontrado")
    void deveFalharAoTentarDeletarItemCardapioNaoEncontrado() {
        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarItemCardapioUseCase.deletar(idInexistente, sessionMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Item cardápio não encontrado."));

        verify(itemCardapioGateway, never()).deletar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o tipo de usuário não for DONO_RESTAURANTE")
    void deveFalhaAutorizacaoTipoUsuarioIncorreto() {

        sessionMock.setAttribute("tipoUsuario", tipoCliente);
        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemCardapio));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarItemCardapioUseCase.deletar(itemCardapioId, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Somente usuários do tipo DONO_RESTAURANTE podem excluir"));

        verify(itemCardapioGateway, times(1)).buscarPorId(itemCardapioId);
        verify(itemCardapioGateway, never()).deletar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o tipo de usuário for nulo na sessão")
    void deveFalharAutorizacaoTipoUsuarioNulo() {

        sessionMock.setAttribute("cpfUsuario", cpfDono);
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemCardapio));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarItemCardapioUseCase.deletar(itemCardapioId, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Somente usuários do tipo DONO_RESTAURANTE podem excluir"));
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o CPF logado não for o dono do restaurante")
    void deveFalharAutorizacaoCPFDiferente() {

        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", cpfOutroDono); // CPF diferente do dono do item
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemCardapio));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarItemCardapioUseCase.deletar(itemCardapioId, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Usuário logado não tem permissão para excluir este item."));

        verify(itemCardapioGateway, times(1)).buscarPorId(itemCardapioId);
        verify(itemCardapioGateway, never()).deletar(any());
    }

    @Test
    @DisplayName("Deve lançar 403 FORBIDDEN se o CPF for nulo na sessão (após passar no tipo)")
    void deveFalharAutorizacaoCPFNulo() {

        sessionMock.setAttribute("tipoUsuario", tipoDono);
        sessionMock.setAttribute("cpfUsuario", null); // CPF nulo
        when(itemCardapioGateway.buscarPorId(itemCardapioId)).thenReturn(Optional.of(itemCardapio));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            deletarItemCardapioUseCase.deletar(itemCardapioId, sessionMock);
        });

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Usuário logado não tem permissão para excluir este item."));
    }
}