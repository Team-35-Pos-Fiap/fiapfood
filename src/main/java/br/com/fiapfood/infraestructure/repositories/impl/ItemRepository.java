package br.com.fiapfood.infraestructure.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoInputDto;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.infraestructure.entities.ItemEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.IItemRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.IItemJpaRepository;

@Repository
public class ItemRepository implements IItemRepository {

	private final IItemJpaRepository itemRepository;
	private final Integer QUANTIDADE_REGISTROS = 5;

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
	
	@Override
	public ItemPaginacaoInputDto buscarTodos(final Integer pagina) {
		Page<ItemEntity> dados = itemRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));
		
		if (!dados.toList().isEmpty()) {
			return ItemPresenter.toInputPaginacaoInputDto(dados);
		} else {
			return null;
		}
	}
	
	@Override
	public void salvar(ItemEntity item) {
		itemRepository.save(item);
	}
}