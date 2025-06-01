package br.com.fiapfood.entities.record.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRecordResponse(@Schema(description = "Matrícula do usuário.", example = "matricula") String matricula) { }