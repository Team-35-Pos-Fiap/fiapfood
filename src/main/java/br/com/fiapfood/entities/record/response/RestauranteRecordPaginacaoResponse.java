package br.com.fiapfood.entities.record.response;

import java.util.List;

public record RestauranteRecordPaginacaoResponse(List<RestauranteRecordResponse> restaurantes,
                                                 PaginacaoRecordResponse dadosPaginacao) { }