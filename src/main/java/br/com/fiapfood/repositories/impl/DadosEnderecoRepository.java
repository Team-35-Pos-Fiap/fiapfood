package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.repositories.interfaces.IDadosEnderecoRepository;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;
import br.com.fiapfood.repositories.interfaces.jpa.IDadosEnderecoJpaRepository;

@Repository
public class DadosEnderecoRepository implements IDadosEnderecoRepository {

	private final IDadosEnderecoJpaRepository dadosEnderecoRepository;

	public DadosEnderecoRepository(IDadosEnderecoJpaRepository dadosEnderecoRepository) {
		this.dadosEnderecoRepository = dadosEnderecoRepository;
	}

	@Override
	public Optional<DadosEnderecoEntity> recuperaDadosEndereco(UUID idUsuario) {
		return dadosEnderecoRepository.findById(idUsuario);
	}
}