package br.com.fiapfood.entities.record.response;

import br.com.fiapfood.entities.record.request.DadosEnderecoRecord;

public record UsuarioRecordResponse(Integer id, 
									String nome, 
									String email, 
									String matricula,
									Boolean ativo, 
									DadosEnderecoRecord dadosEndereco,
									PerfilRecordResponse perfilAcesso) { }