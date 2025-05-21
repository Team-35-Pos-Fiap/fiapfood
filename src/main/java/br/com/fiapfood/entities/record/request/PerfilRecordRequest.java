package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerfilRecordRequest(@NotNull(message = "É necessário informar o perfil de acesso para o usuário.")
                                  @Schema(description = "ID do perfil de acesso do usuário.", example = "1")
                                  Integer idPerfil) { }
