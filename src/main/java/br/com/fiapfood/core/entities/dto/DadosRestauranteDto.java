package br.com.fiapfood.core.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosRestauranteDto(
        @NotBlank (message = "O campo nome precisa ser informado.")
        String nome,

        @NotNull(message = "O campo endereco precisa ser informado.")
        UUID endereco,

        @NotBlank(message = "O campo tipoCozinha precisa ser informado.")
        String tipoCozinha,

        @NotNull(message = "O campo horarioFuncionamento precisa ser informado.")
        LocalDateTime horarioFuncionamento,

        @NotNull(message = "O campo donoRestaurante precisa ser informado.")
        UUID donoRestaurante
) {}
