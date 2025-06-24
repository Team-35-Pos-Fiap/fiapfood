package br.com.fiapfood.entities.record.request;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record RestauranteRecordRequest(
        @NotBlank (message = "O campo nome precisa ser informado.")
        String nome,

        @NotBlank(message = "O campo endereco precisa ser informado.")
        EnderecoRecordRequest endereco,

        @NotBlank(message = "O campo tipoCozinha precisa ser informado.")
        String tipoCozinha,

        @NotBlank(message = "O campo horarioFuncionamento precisa ser informado.")
        LocalDateTime horarioFuncionamento,

        @NotBlank(message = "O campo donoRestaurante precisa ser informado.")
        UsuarioRecordRequest donoRestaurante
) {}
