package br.com.fiapfood.core.entities.dto;

import java.util.List;

public record DadosCardapioComPaginacaoDto(List<CardapioDto> cardapios,
                                           PaginacaoDto dadosPaginacao) { }