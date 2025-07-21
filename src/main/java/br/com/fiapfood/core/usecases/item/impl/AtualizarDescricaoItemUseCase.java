package br.com.fiapfood.core.usecases.item.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDescricaoItemUseCase;

public class AtualizarDescricaoItemUseCase implements IAtualizarDescricaoItemUseCase {

	private final IItemGateway itemGateway;
	private final IRestauranteGateway restauranteGateway;
	private final IImagemGateway imagemGateway;
	
	public AtualizarDescricaoItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
		this.imagemGateway = imagemGateway;
	}
	
	@Override
	public void atualizar(UUID id, String descricao) {
		final Item item = buscarItem(id);
		
		atualizarDescricao(item, descricao);

		salvar(item);
	}

	private void atualizarDescricao(Item item, String descricao) {
		item.atualizarDescricao(descricao);
	}

	private Item buscarItem(final UUID id) {
		return itemGateway.buscarPorId(id);
	}
	
	private Restaurante buscarRestaurante(UUID idRestaurante) {
		return restauranteGateway.buscarPorId(idRestaurante);
	}

	private Imagem buscarImagem(UUID idImagem) {
		return imagemGateway.buscarPorId(idImagem);
	}
	
	private void salvar(Item item) {
		itemGateway.salvar(ItemPresenter.toItemDto(item, null, buscarRestaurante(item.getIdRestaurante())), 
				           ImagemPresenter.toImagemDto(buscarImagem(item.getIdImagem())));
	}
}