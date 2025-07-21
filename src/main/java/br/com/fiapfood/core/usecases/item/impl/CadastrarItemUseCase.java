package br.com.fiapfood.core.usecases.item.impl;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.ICadastrarItemUseCase;

public class CadastrarItemUseCase implements ICadastrarItemUseCase{

	private final IItemGateway itemGateway;
	private final IRestauranteGateway restauranteGateway;
	
	public CadastrarItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		this.itemGateway = itemGateway;
		this.restauranteGateway = restauranteGateway;
	}

	private void salvar(Item item, Imagem imagem, Restaurante restaurante) {
		itemGateway.salvar(ItemPresenter.toItemDto(item, null, restaurante), ImagemPresenter.toImagemDto(imagem));
	}

	@Override
	public void cadastrar(String nome, String descricao, BigDecimal preco, Boolean disponivelParaConsumoPresencial, ImagemCoreDto dadosImagem, UUID idRestaurante) {
		salvar(toItem(nome, descricao, preco, disponivelParaConsumoPresencial, idRestaurante), 
			   toImagem(dadosImagem), 
			   buscarRestaurante(idRestaurante));
	}	
	
	private Restaurante buscarRestaurante(UUID idRestaurante) {
		return restauranteGateway.buscarPorId(idRestaurante);
	}
	
	private Item toItem(String nome, String descricao, BigDecimal preco, Boolean disponivelParaConsumoPresencial, UUID idRestaurante) {
		return ItemPresenter.toItem(nome, descricao, preco, disponivelParaConsumoPresencial, idRestaurante);
	}
	
	private Imagem toImagem(ImagemCoreDto dadosImagem) {
		return ImagemPresenter.toImagem(dadosImagem);
	}
}