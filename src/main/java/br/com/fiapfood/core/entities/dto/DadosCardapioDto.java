package br.com.fiapfood.core.entities.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCardapioDto(
        @NotBlank(message = "O campo nome precisa ser informado.")
        String nome,

        @NotBlank(message = "O campo descricao precisa ser informado.")
        String descricao,

        @NotNull(message = "O campo preco precisa ser informado.")
        @Digits(integer = 6, fraction = 2)
        Double preco,

        @NotNull(message = "O campo disponivelApenasRestaurante precisa ser informado.")
        Boolean disponivelApenasRestaurante,

        @NotBlank(message = "O campo fotoPrato precisa ser informado.")
        String fotoPrato
) { }
