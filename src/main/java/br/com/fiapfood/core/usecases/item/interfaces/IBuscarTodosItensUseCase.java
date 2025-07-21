package br.com.fiapfood.core.usecases.item.interfaces;

import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoOutputCoreDto;

public interface IBuscarTodosItensUseCase {
	ItemPaginacaoOutputCoreDto buscar(Integer pagina);
}