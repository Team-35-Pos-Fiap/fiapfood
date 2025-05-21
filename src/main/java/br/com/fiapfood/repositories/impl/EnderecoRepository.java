package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;

@Repository
public class EnderecoRepository {

	@Autowired
	private IEnderecoRepository enderecoRepository;
	
	public Optional<EnderecoEntity> buscarPorId(UUID id) {
		return enderecoRepository.findById(id);
	}

	public void salvar(EnderecoEntity endereco) {
		enderecoRepository.save(endereco);
	}
}