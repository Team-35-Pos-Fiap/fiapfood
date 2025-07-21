package br.com.fiapfood.core.entities.dto.item;

import java.util.List;

import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;

public record ItemPaginacaoInputDto (List<ItemInputDto> itens, PaginacaoCoreDto paginacao) {

}