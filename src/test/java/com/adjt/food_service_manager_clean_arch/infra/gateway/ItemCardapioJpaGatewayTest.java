package com.adjt.food_service_manager_clean_arch.infra.gateway;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.infra.database.entity.ItemCardapioEntity;
import com.adjt.food_service_manager_clean_arch.infra.database.repository.ItemCardapioRepository;
import com.adjt.food_service_manager_clean_arch.infra.mapper.ItemCardapioEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioJpaGatewayTest {

    @Mock
    private ItemCardapioRepository repository;

    @Mock
    private ItemCardapioEntityMapper mapper;

    @InjectMocks
    private ItemCardapioJpaGateway itemCardapioJpaGateway;

    private final Long ITEM_ID = 100L;
    private final String NOME_ITEM = "Hamburguer Clássico";
    private ItemCardapio itemCardapioDomain;
    private ItemCardapioEntity itemCardapioEntity;

    @BeforeEach
    void setUp() {
        itemCardapioDomain = new ItemCardapio(
                ITEM_ID,
                NOME_ITEM,
                "Pão, carne e queijo",
                25.00,
                true,
                "foto.jpg",
                new Restaurante(1L, "Restaurante A", "Rua X", "Fast Food", "10h-23h", null)
        );

        itemCardapioEntity = new ItemCardapioEntity();
        itemCardapioEntity.setId(ITEM_ID);
        itemCardapioEntity.setNome(NOME_ITEM);
    }

    @Test
    @DisplayName("Deve mapear, salvar a entidade e retornar o Domínio")
    void deveSalvarEConverterComSucesso() {

        when(mapper.toEntity(itemCardapioDomain)).thenReturn(itemCardapioEntity);
        when(repository.save(itemCardapioEntity)).thenReturn(itemCardapioEntity);
        when(mapper.toItemCardapio(itemCardapioEntity)).thenReturn(itemCardapioDomain);

        ItemCardapio resultado = itemCardapioJpaGateway.criarItemCardapio(itemCardapioDomain);

        assertNotNull(resultado);
        assertEquals(ITEM_ID, resultado.getId());

        verify(mapper, times(1)).toEntity(itemCardapioDomain);
        verify(repository, times(1)).save(itemCardapioEntity);
        verify(mapper, times(1)).toItemCardapio(itemCardapioEntity);
    }

    @Test
    @DisplayName("Deve delegar a chamada para criarItemCardapio()")
    void deveChamarCriarItemCardapio() {

        when(mapper.toEntity(itemCardapioDomain)).thenReturn(itemCardapioEntity);
        when(repository.save(itemCardapioEntity)).thenReturn(itemCardapioEntity);
        when(mapper.toItemCardapio(itemCardapioEntity)).thenReturn(itemCardapioDomain);

        ItemCardapio resultado = itemCardapioJpaGateway.salvar(itemCardapioDomain);

        assertNotNull(resultado);

        verify(mapper, times(1)).toEntity(itemCardapioDomain);
        verify(repository, times(1)).save(itemCardapioEntity);
        verify(mapper, times(1)).toItemCardapio(itemCardapioEntity);
    }

    @Test
    @DisplayName("Deve mapear para Entidade e chamar delete no Repository")
    void deveMapearEChamarDelete() {
        when(mapper.toEntity(itemCardapioDomain)).thenReturn(itemCardapioEntity);
        doNothing().when(repository).delete(itemCardapioEntity);

        itemCardapioJpaGateway.deletar(itemCardapioDomain);

        verify(mapper, times(1)).toEntity(itemCardapioDomain);
        verify(repository, times(1)).delete(itemCardapioEntity);
    }

    @Test
    @DisplayName("Deve retornar Optional com ItemCardapio quando encontrado")
    void deveRetornarOptionalComItemCardapio() {
        when(repository.findById(ITEM_ID)).thenReturn(Optional.of(itemCardapioEntity));
        when(mapper.toItemCardapio(itemCardapioEntity)).thenReturn(itemCardapioDomain);

        Optional<ItemCardapio> resultado = itemCardapioJpaGateway.buscarPorId(ITEM_ID);

        assertTrue(resultado.isPresent());
        assertEquals(ITEM_ID, resultado.get().getId());
        verify(repository, times(1)).findById(ITEM_ID);
        verify(mapper, times(1)).toItemCardapio(itemCardapioEntity);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando não encontrado")
    void deveRetornarOptionalVazio() {
        when(repository.findById(ITEM_ID)).thenReturn(Optional.empty());

        Optional<ItemCardapio> resultado = itemCardapioJpaGateway.buscarPorId(ITEM_ID);

        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById(ITEM_ID);

        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve retornar lista de ItensCardapio e mapear todos")
    void deveRetornarListaCompleta() {
        ItemCardapioEntity entity2 = new ItemCardapioEntity();
        ItemCardapio domain2 = new ItemCardapio(101L, "Refrigerante", "Lata", 8.00, true, "foto2.jpg", null);

        List<ItemCardapioEntity> entities = Arrays.asList(itemCardapioEntity, entity2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toItemCardapio(itemCardapioEntity)).thenReturn(itemCardapioDomain);
        when(mapper.toItemCardapio(entity2)).thenReturn(domain2);

        List<ItemCardapio> resultados = itemCardapioJpaGateway.buscarTodosItensCardapio();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertEquals(NOME_ITEM, resultados.get(0).getNome());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).toItemCardapio(any(ItemCardapioEntity.class));
    }
}