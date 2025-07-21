package br.com.fiapfood.core.usecases.item.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.item.ItemOutputCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarItemPorIdUseCase;

public class BuscarItemPorIdUseCase implements IBuscarItemPorIdUseCase{

	private final IItemGateway itemGateway;
	private final IRestauranteGateway restauranteGateway;
	
	private final String BASE_LINK_FOTO = "http://localhost:8080/fiapfood/itens/";
	private final String CONTEXTO_LINK_FOTO = "/imagem";

	public BuscarItemPorIdUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
	}
	
	@Override
	public ItemOutputCoreDto buscar(final UUID id) {
		return toItemOutputDto(buscarItem(id));
	}

	private String prepararLinkFoto(final UUID id) {
		return BASE_LINK_FOTO + id + CONTEXTO_LINK_FOTO;
	}
	
	private Restaurante buscarRestaurante(UUID idRestaurante) {
		return restauranteGateway.buscarPorId(idRestaurante);
	}
	
	private ItemOutputCoreDto toItemOutputDto(Item item) {
		return ItemPresenter.toItemDto(item, prepararLinkFoto(item.getId()), buscarRestaurante(item.getIdRestaurante()));
	}
	
	private Item buscarItem(UUID id) {
		return itemGateway.buscarPorId(id);
	}
}