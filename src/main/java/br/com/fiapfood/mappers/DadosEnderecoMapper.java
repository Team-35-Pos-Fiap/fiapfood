package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.domain.DadosEnderecoDomain;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

public abstract class DadosEnderecoMapper {

	// record -> domain -> entity
	
	// 1 - record -> domain
	public static DadosEnderecoDomain toDadosEndereco(EnderecoRecordRequest dadosEnderecoRecord) {
		return new DadosEnderecoDomain(null,
								 dadosEnderecoRecord.cidade(),
								 dadosEnderecoRecord.cep(),
								 dadosEnderecoRecord.bairro(),
								 dadosEnderecoRecord.endereco(),
								 dadosEnderecoRecord.estado(),
								 dadosEnderecoRecord.numero(),
								 dadosEnderecoRecord.complemento(),
								 dadosEnderecoRecord.semNumero(),
								 null);
	}
	
	// 2 - domain -> entity
	public static EnderecoEntity toDadosEndereco(DadosEnderecoDomain dadosEndereco) {
		return new EnderecoEntity(dadosEndereco.getId(),
									   dadosEndereco.getCidade(),
									   dadosEndereco.getCep(),
									   dadosEndereco.getBairro(),
									   dadosEndereco.getEndereco(),
									   dadosEndereco.getEstado(),
									   dadosEndereco.getNumero(),
									   dadosEndereco.getComplemento(),
									   dadosEndereco.getSemNumero(),
									   null//UsuarioMapper.toUsuarioEntity(dadosEndereco.getUsuario())
									   );
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	public static DadosEnderecoDomain toDadosEndereco(EnderecoEntity dadosEndereco) {
		return new DadosEnderecoDomain(dadosEndereco.getId(),
							     dadosEndereco.getCidade(),
								 dadosEndereco.getCep(),
							     dadosEndereco.getBairro(),
							     dadosEndereco.getEndereco(),
							     dadosEndereco.getEstado(),
							     dadosEndereco.getNumero(),
							     dadosEndereco.getComplemento(),
							     dadosEndereco.getSemNumero(),
							     null);
	}
	
	// 4 - domain -> record
	public static EnderecoRecordRequest toDadosEnderecoRecord(DadosEnderecoDomain dadosEndereco) {
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