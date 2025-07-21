package br.com.fiapfood.core.usecases.item.impl;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemOutputCoreDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoOutputCoreDto;
import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarTodosItensUseCase;

public class BuscarTodosItensUseCase implements IBuscarTodosItensUseCase{

	private final IItemGateway itemGateway;
	private final IRestauranteGateway restauranteGateway;
	
	private final String BASE_LINK_FOTO = "http://localhost:8080/fiapfood/itens/";
	private final String CONTEXTO_LINK_FOTO = "/imagem";

	public BuscarTodosItensUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
	}
	
	@Override
	public ItemPaginacaoOutputCoreDto buscar(final Integer pagina) {
		final ItemPaginacaoInputDto dados = buscarItens(pagina);
		
		return toItemPaginacaoOutputDto(toListOutputDto(toList(dados.itens())), dados.paginacao());	
	}
	
	private List<ItemOutputCoreDto> toListOutputDto(List<Item> itens) {
		return itens.stream().map(i -> ItemPresenter.toItemDto(i, prepararLinkFoto(i.getId()), buscarRestaurante(i.getIdRestaurante()))).toList();
	};
	
	private String prepararLinkFoto(final UUID id) {
		return BASE_LINK_FOTO + id + CONTEXTO_LINK_FOTO;
	}
	
	private ItemPaginacaoOutputCoreDto toItemPaginacaoOutputDto(List<ItemOutputCoreDto> itens, PaginacaoCoreDto paginacao) {
		return ItemPresenter.toInputPaginacaoOutputDto(itens, paginacao);
	}
	
	private Restaurante buscarRestaurante(UUID idRestaurante) {
		return restauranteGateway.buscarPorId(idRestaurante);
	}
	
	private ItemPaginacaoInputDto buscarItens(Integer pagina) {
		return itemGateway.buscarTodos(pagina);
	}
	
	private List<Item> toList(List<ItemInputDto> itens) {
		return ItemPresenter.toList(itens);
	}
}