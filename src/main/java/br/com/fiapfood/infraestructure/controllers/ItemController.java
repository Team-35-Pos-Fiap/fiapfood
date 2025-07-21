package br.com.fiapfood.infraestructure.controllers;


import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.fiapfood.core.controllers.interfaces.IItemCoreController;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.infraestructure.controllers.request.item.DescricaoDto;
import br.com.fiapfood.infraestructure.controllers.request.item.DisponibilidadeDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemPaginacaoDto;
import br.com.fiapfood.infraestructure.controllers.request.item.NomeDto;
import br.com.fiapfood.infraestructure.controllers.request.item.PrecoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/itens")
@Slf4j
public class ItemController {

	private final IItemCoreController itemCoreController;

	public ItemController(IItemCoreController itemCoreController) {
		this.itemCoreController = itemCoreController;
	}

	@GetMapping
	public ResponseEntity<ItemPaginacaoDto> buscarItens(@RequestParam(defaultValue = "1") final Integer pagina) {
		log.info("buscarItens() - pagina {}", pagina);

		return ResponseEntity.ok().body(itemCoreController.buscarTodos(pagina));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemDto> buscarItemPorid(@PathVariable @NotNull @Valid final UUID id) {
		log.info("buscarItemPorid():id {}", id);

		return ResponseEntity.ok().body(itemCoreController.buscarPorId(id));
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })	
	public ResponseEntity<Void> cadastrar(@Valid @NotNull @RequestParam String nome, @RequestParam String descricao, 
										  @NotNull @RequestParam BigDecimal preco, @NotNull @RequestParam Boolean disponivelParaConsumoPresencial, 
										  @NotNull @RequestParam MultipartFile imagem, @NotNull @RequestParam UUID idRestaurante) {
		itemCoreController.cadastrar(nome, descricao, preco, disponivelParaConsumoPresencial, imagem, idRestaurante);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("{id}/descricao")
	public ResponseEntity<Void> atualizarDescricao(@PathVariable @NotNull @Valid final UUID id, @RequestBody @NotNull final DescricaoDto dados) {
		itemCoreController.atualizarDescricao(id, dados.descricao());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PatchMapping("{id}/nome")
	public ResponseEntity<Void> atualizarNome(@PathVariable @NotNull @Valid final UUID id, @RequestBody @NotNull final NomeDto dados) {
		itemCoreController.atualizarNome(id, dados.nome());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PatchMapping("{id}/preco")
	public ResponseEntity<Void> atualizarPreco(@PathVariable @NotNull @Valid final UUID id, @RequestBody @NotNull final PrecoDto dados) {
		itemCoreController.atualizarPreco(id, dados.preco());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("{id}/disponibilidade-consumo-presencial")
	public ResponseEntity<Void> atualizarDisponibilidadeConsumoPresencial(@PathVariable @NotNull @Valid final UUID id, @RequestBody @NotNull final DisponibilidadeDto dados) {
		itemCoreController.atualizarDisponibilidadeConsumoPresencial(id, dados.isDisponivel());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PatchMapping("{id}/disponibilidade")
	public ResponseEntity<Void> atualizarDisponibilidade(@PathVariable @NotNull @Valid final UUID id, @RequestBody @NotNull final DisponibilidadeDto dados) {
		itemCoreController.atualizarDisponibilidade(id, dados.isDisponivel());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PatchMapping("{id}/imagem")
	public ResponseEntity<Void> atualizarImagem(@PathVariable @NotNull @Valid final UUID id, @NotNull @RequestParam final MultipartFile imagem) {
		itemCoreController.atualizarImagem(id, imagem);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("{id}/imagem")
	public ResponseEntity<?> baixarImagem(@PathVariable @NotNull @Valid final UUID id) {
		ImagemCoreDto imagem = itemCoreController.baixarImagem(id);
		
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagem.nome() + "\"").body(imagem.conteudo());
	}
}