package com.adjt.food_service_manager_clean_arch.infra.web.rest;

import com.adjt.food_service_manager_clean_arch.core.domain.ItemCardapio;
import com.adjt.food_service_manager_clean_arch.core.domain.Restaurante;
import com.adjt.food_service_manager_clean_arch.core.dto.CriarItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.dto.RespostaItemCardapioDto;
import com.adjt.food_service_manager_clean_arch.core.usecase.cardapio.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/item-cardapio")
public class ItemCardapioApiController {

    private final CadastrarItemCardapioUseCaseImpl itemCardapioController;
    private final BuscarItemCardapioUseCaseImpl buscarItemCardapioController;
    private final ListarTodosItensCardapioUseCaseImpl listarItemCardapioController;
    private final AtualizarItemCardapioUseCaseImpl atualizarItemCardapioController;
    private final DeletarItemCardapioUseCaseImpl deletarItemCardapioUseController;

    @PostMapping
    public ResponseEntity<RespostaItemCardapioDto> criarItemCardapio(@RequestBody CriarItemCardapioDto itemCardapioDto, HttpSession session) {
        ItemCardapio itemCardapio = itemCardapioController.criarItemCardapio(itemCardapioDto, session);
        log.info("Item de cardápio criado com ID: {}, nome: {}", itemCardapio.getId(), itemCardapio.getNome());
        return ResponseEntity.status(HttpStatus.CREATED).body(map(itemCardapio));
    }

    @GetMapping
    public ResponseEntity<List<RespostaItemCardapioDto>> listarItensCardapio() {
        List<ItemCardapio> itensCardapio = listarItemCardapioController.listarTodosItemCardapioUseCase();
        log.info("Listando todos os Itens do Cardápio !");
        List<RespostaItemCardapioDto> respostaItemCardapioDtos = itensCardapio.stream().map(this::map).toList();
        return ResponseEntity.ok(respostaItemCardapioDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RespostaItemCardapioDto> buscarPorId(@PathVariable Long id) {
        ItemCardapio itemCardapio = buscarItemCardapioController.buscarItemCardapio(id);
        log.info("Item do cardápio encontrado: {}, id:", itemCardapio.getId());
        return ResponseEntity.ok(map(itemCardapio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaItemCardapioDto> atualizarItemCardapio(
            @PathVariable Long id,
            @RequestBody CriarItemCardapioDto itemCardapioDto,
            HttpSession session
    ){
        ItemCardapio atualizado = atualizarItemCardapioController.atualizar(id, itemCardapioDto, session);
        log.info("Item do cardápio atualizado: ID:{}, Nome={}",atualizado.getId(),atualizado.getNome());
        return ResponseEntity.ok(map(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id, HttpSession session){
        deletarItemCardapioUseController.deletar(id,session);
        return ResponseEntity.noContent().build();
    }

    public RespostaItemCardapioDto map(ItemCardapio itemCardapio) {
        if(itemCardapio == null) return null;
        Restaurante restaurante = itemCardapio.getRestaurante();
        String nomeRestaurante = (restaurante != null ) ? restaurante.getNome() : "Restaurante Não Encontrado";
		return RespostaItemCardapioDto.builder()
                .id(itemCardapio.getId())
				.nome(itemCardapio.getNome())
				.descricao(itemCardapio.getDescricao())
				.preco(itemCardapio.getPreco())
                .disponibilidade(itemCardapio.getDisponibilidade())
                .foto(itemCardapio.getFoto())
				.nomeRestaurante(nomeRestaurante)
				.build();
	}
}
