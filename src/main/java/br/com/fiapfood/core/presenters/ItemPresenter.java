package br.com.fiapfood.core.presenters;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemOutputCoreDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoOutputCoreDto;
import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemPaginacaoDto;
import br.com.fiapfood.infraestructure.entities.ItemEntity;

public class ItemPresenter {

	public static ItemInputDto toItemDto(ItemEntity item) {
		return new ItemInputDto(item.getId(), item.getNome(), item.getDescricao(), 
								item.getPreco(), item.getIsDisponivelConsumoPresencial(), 
								item.getIsDisponivel(), item.getImagem().getId(), item.getRestaurante().getId());
	}
	
	public static ItemOutputCoreDto toItemDto(Item item, String linkFoto, Restaurante restaurante) {
		return new ItemOutputCoreDto(item.getId(), item.getNome(), item.getDescricao(), item.getPreco(), 
								 item.getIsDisponivelConsumoPresencial(), item.getIsDisponivel(), linkFoto, RestaurantePresenter.toRestauranteResumidoDto(restaurante));
	}

	public static Item toItem(ItemInputDto item) {
		return Item.criar(item.id(), item.nome(), item.descricao(), item.preco(), item.isDisponivelApenasRestaurante(), item.isDisponivel(), item.idImagem(), item.idRestaurante());
	}

	public static ItemPaginacaoInputDto toInputPaginacaoInputDto(Page<ItemEntity> dados) {
		List<ItemInputDto> itens = dados.toList()
									    .stream()
									    .map(i -> ItemPresenter.toItemDto(i))
									    .toList();

		PaginacaoCoreDto paginacao = new PaginacaoCoreDto(dados.getNumber() + 1, dados.getTotalPages(), Long.valueOf(dados.getTotalElements()).intValue());
		
		return new ItemPaginacaoInputDto(itens, paginacao);
	}

	public static List<Item> toList(List<ItemInputDto> itens) {
		return itens.stream().map(i -> ItemPresenter.toItem(i)).toList();
	}

	public static ItemPaginacaoOutputCoreDto toInputPaginacaoOutputDto(List<ItemOutputCoreDto> itens, PaginacaoCoreDto paginacao) {
		return new ItemPaginacaoOutputCoreDto(itens, paginacao);
	}
	
	public static Item toItem(String nome, String descricao, BigDecimal preco, Boolean disponivelParaConsumoPresencial, UUID idRestaurante) {
		return Item.criar(null, nome, descricao, preco, disponivelParaConsumoPresencial, true, null, idRestaurante);
	}

	public static ItemEntity toItem(ItemOutputCoreDto item, ImagemCoreDto imagem) {
		return new ItemEntity(item.id(), item.nome(), item.descricao(), item.preco(), 
							  item.isDisponivelConsumoPresencial(), item.isDisponivel(), ImagemPresenter.toImagemEntity(imagem), 
							  RestaurantePresenter.toRestauranteResumidoEntity(item.restaurante().id()));
	}

	public static ItemDto toItemOutputDto(ItemOutputCoreDto item) {
		return new ItemDto(item.id(), item.nome(), item.descricao(), item.preco(),
								 item.isDisponivelConsumoPresencial(), item.isDisponivel(),
								 item.linkFoto(), RestaurantePresenter.toDadosRestauranteResumidoDto(item.restaurante()));
	}

	public static ItemPaginacaoDto ItemPaginacaoOutputCoreDto(ItemPaginacaoOutputCoreDto item) {
		return new ItemPaginacaoDto(ItemPresenter.toListItemOutputDto(item.itens()), PaginacaoPresenter.toPaginacaoDto(item.paginacao()));
	}

	private static List<ItemDto> toListItemOutputDto(List<ItemOutputCoreDto> itens) {
		return itens.stream().map(i -> ItemPresenter.toItemOutputDto(i)).toList();
	}
}