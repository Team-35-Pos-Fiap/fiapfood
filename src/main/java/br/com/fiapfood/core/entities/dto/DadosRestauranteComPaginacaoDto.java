package br.com.fiapfood.core.entities.dto;

import java.util.List;

public record DadosRestauranteComPaginacaoDto(List<RestauranteDto> restaurantes,
                                              PaginacaoDto dadosPaginacao) { }