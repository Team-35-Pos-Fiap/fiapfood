package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRecordRequest(@Schema(description = "Matrícula do usuário", example = "us000001")
								 @NotBlank(message = "O campo matrícula não foi informado.")
								 @Size(min = 3, max = 6, message = "O campo matrícula precisa ter entre 3 e 6 caracteres.")
								 String matricula, 
								 
								 @Schema(description = "Senha do usuário", example = "123456") 
								 @NotBlank(message = "O campo senha não foi informado.") 
								 String senha) { }