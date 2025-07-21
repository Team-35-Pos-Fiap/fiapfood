package br.com.fiapfood.core.gateways.interfaces;import java.util.UUID;

import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.entities.dto.item.ItemOutputCoreDto;
import br.com.fiapfood.core.entities.dto.item.ItemPaginacaoInputDto;

public interface IItemGateway {
	Item buscarPorId(UUID id);
	ItemPaginacaoInputDto buscarTodos(Integer pagina);
	void salvar(ItemOutputCoreDto item, ImagemCoreDto imagem);
}