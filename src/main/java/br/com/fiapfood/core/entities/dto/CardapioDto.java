package br.com.fiapfood.core.entities.dto;

import java.util.UUID;

public record CardapioDto(
        UUID id,
        String nome,
        String descricao,
        Double preco,
        Boolean disponivelApenasRestaurante,
        String fotoPrato
) { }