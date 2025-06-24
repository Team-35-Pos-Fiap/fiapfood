package br.com.fiapfood.entities.record.response;

import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

import java.util.UUID;

public record CardapioRecordResponse(
        UUID id,
        String nome,
        String descricao,
        Double preco,
        Boolean DisponivelApenasRestaurante,
        String fotoPrato
) { }