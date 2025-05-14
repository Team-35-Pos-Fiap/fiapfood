package br.com.fiapfood.entities.record.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenRecordResponse(@Schema(description = "Token de acesso gerado para o acesso da aplicação.", example = "token") String token) { }