package br.com.fiapfood.entities.record.response;

import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

public record UsuarioRecordResponse(Integer id, 
									String nome, 
									String email, 
									String matricula,
									Boolean ativo, 
									EnderecoRecordRequest dadosEndereco,
									PerfilRecordResponse perfilAcesso) { }