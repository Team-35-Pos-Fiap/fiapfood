package br.com.fiapfood.repositories.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;

@Repository
public class EnderecoRepository {

	@Autowired
	private IEnderecoRepository enderecoRepository;
	
	public Optional<EnderecoEntity> buscarPorIdUsuario(Integer idUsuario) {
		return enderecoRepository.findById(idUsuario);
	}

	public void salvar(EnderecoEntity endereco) {
		enderecoRepository.save(endereco);
	}
}