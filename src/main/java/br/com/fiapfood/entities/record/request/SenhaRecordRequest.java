package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record SenhaRecordRequest(@Schema(description = "Senha do usuário", example = "123")
                                 @NotBlank(message = "O campo senha precisa estar preenchido.")
                                 String senha) { }
