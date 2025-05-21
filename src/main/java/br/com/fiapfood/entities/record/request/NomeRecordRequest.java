package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NomeRecordRequest(@Schema(description = "Nome do usuário", example = "Thiago")
                                @NotBlank(message = "O campo nome precisa estar preenchido.")
                                @Size(min = 3, max = 150, message = "O campo nome precisa ter entre 3 e 150 caracteres.")
                                String nome) { }
