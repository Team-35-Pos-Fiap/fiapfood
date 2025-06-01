package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.NotBlank;


public record SenhaRecordRequest(@NotBlank(message = "O campo senha precisa estar preenchido.")
                                 String senha) { }
