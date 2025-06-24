package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardapioRecordRequest(
        @NotBlank(message = "O campo nome precisa ser informado.")
        String nome,

        @NotBlank(message = "O campo descricao precisa ser informado.")
        String descricao,

        @NotBlank(message = "O campo preco precisa ser informado.")
        @Digits(integer = 6, fraction = 2)
        Double preco,

        @NotNull(message = "O campo DisponivelApenasRestaurante precisa ser informado.")
        Boolean DisponivelApenasRestaurante,

        @NotBlank(message = "O campo fotoPrato precisa ser informado.")
        String fotoPrato
) { }
