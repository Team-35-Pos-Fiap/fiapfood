package br.com.fiapfood.core.gateways.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.exceptions.item.ItemNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.IItemRepository;

public class ItemGateway implements IItemGateway {

	private final IItemRepository itemRepository;
	
	public ItemGateway(IItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	@Override
	public Item buscarPorId(final UUID id) {
		final ItemInputDto item = itemRepository.buscarPorId(id);
		
		if(item != null) {
			return ItemPresenter.toItem(item);
		} else {
			throw new ItemNaoEncontradoException("Não foi encontrado nenhum item com o id informado.");			
		}
	}
}