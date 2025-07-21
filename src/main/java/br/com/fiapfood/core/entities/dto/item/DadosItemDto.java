package br.com.fiapfood.core.entities.dto.item;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.fiapfood.core.entities.dto.restaurante.DadosRestauranteDto;

public record DadosItemDto(UUID id, String nome, String descricao, BigDecimal preco, 
							Boolean isDisponivelConsumoPresencial, Boolean isDisponivel, 
							String linkFoto, DadosRestauranteDto restaurante) {

}