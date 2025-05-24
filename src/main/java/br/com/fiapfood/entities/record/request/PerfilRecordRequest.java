package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerfilRecordRequest(@NotNull(message = "É necessário informar o perfil de acesso para o usuário.")
                                  Integer idPerfil) { }
