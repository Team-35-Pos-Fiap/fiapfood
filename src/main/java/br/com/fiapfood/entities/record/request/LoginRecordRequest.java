package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRecordRequest(@NotBlank(message = "O campo matrícula não foi informado.")
								 @Size(min = 3, max = 6, message = "O campo matrícula precisa ter entre 3 e 6 caracteres.")
								 String matricula, 
								 
								 @NotBlank(message = "O campo senha não foi informado.")
								 String senha) { }