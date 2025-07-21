package br.com.fiapfood.core.entities.dto.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemInputDto(UUID id, String nome, String descricao, BigDecimal preco, 
						   Boolean isDisponivelApenasRestaurante, Boolean isDisponivel, UUID idImagem, UUID idRestaurante) {

}
