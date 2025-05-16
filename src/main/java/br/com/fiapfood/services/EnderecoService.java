
package br.com.fiapfood.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.repositories.impl.EnderecoRepository;
import br.com.fiapfood.repositories.impl.UsuarioRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void atualizarEndereco(Integer id, EnderecoRecordRequest dados) {
			
		EnderecoEntity endereco = trataDadosEndereco(id, dados);
		
		enderecoRepository.salvar(endereco);
	}
	
	private EnderecoEntity trataDadosEndereco(Integer id, EnderecoRecordRequest enderecoRecord) {
		EnderecoEntity endereco = null;
		
		Optional<EnderecoEntity> dados = enderecoRepository.buscarPorIdUsuario(id);
		
		if (dados.isPresent()) {
			endereco = dados.get();
		} else {
			endereco = new EnderecoEntity();
			
			endereco.atualizarUsuario(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id));
		}

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