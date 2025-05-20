package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;
import br.com.fiapfood.repositories.interfaces.IDadosEnderecoRepository;

@Repository
public class DadosEnderecoRepository {

	@Autowired
	private IDadosEnderecoRepository dadosEnderecoRepository;
	
	public Optional<DadosEnderecoEntity> recuperaDadosEndereco(UUID idUsuario) {
		return dadosEnderecoRepository.findById(idUsuario);
	}
}