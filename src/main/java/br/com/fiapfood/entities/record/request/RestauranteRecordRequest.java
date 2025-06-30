package br.com.fiapfood.entities.record.request;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestauranteRecordRequest(
        @NotBlank (message = "O campo nome precisa ser informado.")
        String nome,

        @NotNull(message = "O campo endereco precisa ser informado.")
        EnderecoRecordRequest endereco,

        @NotBlank(message = "O campo tipoCozinha precisa ser informado.")
        String tipoCozinha,

        @NotNull(message = "O campo horarioFuncionamento precisa ser informado.")
        LocalDateTime horarioFuncionamento,

        @NotNull(message = "O campo donoRestaurante precisa ser informado.")
        UsuarioRecordRequest donoRestaurante
) {}
