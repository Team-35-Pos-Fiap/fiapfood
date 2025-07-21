package br.com.fiapfood.core.usecases.item.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IBaixarImagemItemUseCase;

public class BaixarImagemItemUseCase implements IBaixarImagemItemUseCase {

	private final IItemGateway itemGateway;
	private final IImagemGateway imagemGateway;

	public BaixarImagemItemUseCase(IItemGateway itemGateway, IImagemGateway imagemGateway) {
		this.itemGateway = itemGateway;
		this.imagemGateway = imagemGateway;
	}

	@Override
	public ImagemCoreDto baixar(UUID id) {
		final Item item  = buscarItem(id);
		
		return toImagemDto(buscarImagem(item.getIdImagem()));
	}
	
	private Imagem buscarImagem(UUID idImagem) {
		return imagemGateway.buscarPorId(idImagem);
	}

	private Item buscarItem(final UUID id) {
		return itemGateway.buscarPorId(id);
	}
	
	private ImagemCoreDto toImagemDto(Imagem imagem) {
		return ImagemPresenter.toImagemDto(imagem);
	}
}