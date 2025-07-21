package br.com.fiapfood.infraestructure.controllers.request.item;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosRestauranteResumidoDto;

public record ItemDto(UUID id, String nome, String descricao, BigDecimal preco, 
							Boolean isDisponivelConsumoPresencial, Boolean isDisponivel, 
							String linkFoto, DadosRestauranteResumidoDto restaurante) {

}