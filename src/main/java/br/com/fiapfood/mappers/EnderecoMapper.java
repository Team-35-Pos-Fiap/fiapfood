package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.domain.EnderecoDomain;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

public abstract class EnderecoMapper {

	// record -> domain -> entity
	
	// 1 - record -> domain
	public static EnderecoDomain toDadosEndereco(EnderecoRecordRequest dadosEnderecoRecord) {
		return new EnderecoDomain(null,
								 dadosEnderecoRecord.cidade(),
								 dadosEnderecoRecord.cep(),
								 dadosEnderecoRecord.bairro(),
								 dadosEnderecoRecord.endereco(),
								 dadosEnderecoRecord.estado(),
								 dadosEnderecoRecord.numero(),
								 dadosEnderecoRecord.complemento(),
								 dadosEnderecoRecord.semNumero());
	}
	
	// 2 - domain -> entity
	public static EnderecoEntity toDadosEndereco(EnderecoDomain dadosEndereco) {
		return new EnderecoEntity(dadosEndereco.getId(),
									   dadosEndereco.getCidade(),
									   dadosEndereco.getCep(),
									   dadosEndereco.getBairro(),
									   dadosEndereco.getEndereco(),
									   dadosEndereco.getEstado(),
									   dadosEndereco.getNumero(),
									   dadosEndereco.getComplemento(),
									   dadosEndereco.getSemNumero());
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	public static EnderecoDomain toDadosEndereco(EnderecoEntity dadosEndereco) {
		return new EnderecoDomain(dadosEndereco.getId(),
							     dadosEndereco.getCidade(),
								 dadosEndereco.getCep(),
							     dadosEndereco.getBairro(),
							     dadosEndereco.getEndereco(),
							     dadosEndereco.getEstado(),
							     dadosEndereco.getNumero(),
							     dadosEndereco.getComplemento(),
							     dadosEndereco.getSemNumero());
	}
	
	// 4 - domain -> record
	public static EnderecoRecordRequest toDadosEnderecoRecord(EnderecoDomain dadosEndereco) {
		return new EnderecoRecordRequest(dadosEndereco.getCidade(), 
									   dadosEndereco.getCep(),
									   dadosEndereco.getBairro(), 
									   dadosEndereco.getEndereco(),
									   dadosEndereco.getEstado(),
									   dadosEndereco.getNumero(), 
									   dadosEndereco.getComplemento(),
									   dadosEndereco.getSemNumero());
	}
}