package br.com.fiapfood.core.usecases.item.impl;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.exceptions.item.AtualizacaoPrecoItemNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarPrecoItemUseCase;

public class AtualizarPrecoItemUseCase implements IAtualizarPrecoItemUseCase {
	
	private final IItemGateway itemGateway;
	private final IRestauranteGateway restauranteGateway;
	private final IImagemGateway imagemGateway;

	public AtualizarPrecoItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
		this.imagemGateway = imagemGateway;
	}
	
	@Override
	public void atualizar(final UUID id, final BigDecimal preco) {
		final Item item = buscarItem(id);
	
		validaPreco(item, preco);
		atualizarPreco(item, preco);
		
		salvar(item);
	}

	private void validaPreco(Item item, BigDecimal preco) {
		if(item.getPreco().equals(preco)) {
			throw new AtualizacaoPrecoItemNaoPermitidaException("Não é possível atualizar o preço do item para o mesmo valor.");
		}
	}

	private void atualizarPreco(Item item, BigDecimal preco) {
		item.atualizarPreco(preco);		
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
