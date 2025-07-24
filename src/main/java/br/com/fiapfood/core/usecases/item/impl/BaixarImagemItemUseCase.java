package br.com.fiapfood.core.usecases.item.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IBaixarImagemItemUseCase;

public class BaixarImagemItemUseCase implements IBaixarImagemItemUseCase {

	private final IItemGateway itemGateway;

	public BaixarImagemItemUseCase(IItemGateway itemGateway) {
		this.itemGateway = itemGateway;
	}

	@Override
	public ImagemCoreDto baixar(UUID idItem) {
		final Item item  = buscarItem(idItem);
		
		return toImagemDto(item.getImagem());
	}
	
	private Item buscarItem(final UUID id) {
		return itemGateway.buscarPorId(id);
	}
	
	private ImagemCoreDto toImagemDto(Imagem imagem) {
		return ImagemPresenter.toImagemDto(imagem);
	}
}