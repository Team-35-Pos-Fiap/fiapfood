package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.repositories.interfaces.jpa.IEnderecoJpaRepository;

@Repository
public class EnderecoRepository implements IEnderecoRepository {

	private final IEnderecoJpaRepository enderecoRepository;

	public EnderecoRepository(IEnderecoJpaRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
	public Optional<EnderecoEntity> buscarPorId(UUID id) {
		return enderecoRepository.findById(id);
	}

	@Override
	public void salvar(EnderecoEntity endereco) {
		enderecoRepository.save(endereco);
	}
}