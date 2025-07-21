package br.com.fiapfood.infraestructure.repositories.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.dto.item.ItemInputDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoInputDto;
import br.com.fiapfood.infraestructure.entities.ItemEntity;

public interface IItemRepository {
    ItemInputDto buscarPorId(UUID id);
	ItemPaginacaoInputDto buscarTodos(Integer pagina);
	void salvar(ItemEntity item);
}