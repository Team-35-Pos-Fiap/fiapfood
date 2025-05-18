package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailRecordRequest(@Schema(description = "Email do usuário", example = "thiago@fiapfood.com")
                                 @NotBlank(message = "O campo email precisa estar preenchido.")
                                 @Size(min = 3, max = 70, message = "O campo email precisa ter entre 3 e 70 caracteres.")
                                 String email) { }