package br.com.fiapfood.entities.record.response;

import java.util.List;

public record UsuarioRecordPaginacaoResponse(List<UsuarioRecordResponse> usuarios, PaginacaoRecordResponse dadosPaginacao) { }