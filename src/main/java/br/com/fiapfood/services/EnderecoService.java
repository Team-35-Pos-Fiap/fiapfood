
package br.com.fiapfood.services;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;
import br.com.fiapfood.services.interfaces.IEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.repositories.impl.EnderecoRepository;
import br.com.fiapfood.repositories.impl.UsuarioRepository;

@Service
public class EnderecoService implements IEnderecoService {

	private final IEnderecoRepository enderecoRepository;

	public EnderecoService(IEnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
	public void atualizarEndereco(EnderecoEntity enderecoAtual, EnderecoRecordRequest dados) {
		
		enderecoRepository.salvar(trataDadosEndereco(enderecoAtual, dados));
	}
	
	private EnderecoEntity trataDadosEndereco(EnderecoEntity endereco, EnderecoRecordRequest enderecoRecord) {
		endereco.atualizarDados(enderecoRecord.endereco(), 
							 	enderecoRecord.cidade(), 
							 	enderecoRecord.bairro(), 
							 	enderecoRecord.estado(), 
							 	enderecoRecord.numero(), 
							 	enderecoRecord.cep(), 
							 	enderecoRecord.complemento(), 
							 	enderecoRecord.semNumero());
		
		return endereco;
	}
}