package br.com.fiapfood.core.entities.dto.restaurante;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;

public record DadosRestauranteDto(UUID id, String nome, UUID idEndereco, UUID idDono, 
        						  Integer idTipoCulinaria, Boolean isAtivo,
        						  List<AtendimentoCoreDto> atendimentos) {
}