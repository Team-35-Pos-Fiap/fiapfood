package br.com.fiapfood.entities.record.response;

import java.util.List;

public record CardapioRecordPaginacaoResponse(List<CardapioRecordResponse> cardapios,
                                              PaginacaoRecordResponse dadosPaginacao) { }