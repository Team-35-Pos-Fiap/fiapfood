package br.com.fiapfood.infraestructure.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.infraestructure.entities.ItemEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.IItemRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.IItemJpaRepository;

@Repository
public class ItemRepository implements IItemRepository {

	private final IItemJpaRepository itemRepository;

	public ItemRepository(IItemJpaRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public ItemInputDto buscarPorId(final UUID id) {
		final Optional<ItemEntity> item = itemRepository.findById(id);

		if(item.isPresent()) {
			return ItemPresenter.toItemDto(item.get());
		} else {
			return null;
		}
	}
}