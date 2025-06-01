package br.com.fiapfood.entities.record.response;

import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

import java.util.UUID;

public record UsuarioRecordResponse(UUID id,
									String nome,
									String email,
									String matricula,
									Boolean ativo,
									EnderecoRecordRequest dadosEndereco,
									PerfilRecordResponse perfilAcesso) { }