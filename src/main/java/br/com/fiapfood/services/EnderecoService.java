
package br.com.fiapfood.services;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.entities.db.UsuarioEntity;
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
	
	public void atualizarEndereco(UUID idUsuario, EnderecoRecordRequest dados) {

		EnderecoEntity endereco = trataDadosEndereco(idUsuario, dados);
		
		enderecoRepository.salvar(endereco);
	}
	
	private EnderecoEntity trataDadosEndereco(UUID idUsuario, EnderecoRecordRequest enderecoRecord) {
		EnderecoEntity endereco = null;
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(idUsuario);

		Optional<EnderecoEntity> dados = enderecoRepository.buscarPorId(usuario.getDadosEndereco().getId());
		
		if (dados.isPresent()) {
			endereco = dados.get();
		} else {
			endereco = new EnderecoEntity();
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