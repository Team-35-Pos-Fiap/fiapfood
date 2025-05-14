package br.com.fiapfood.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;

public interface IDadosEnderecoRepository extends JpaRepository<DadosEnderecoEntity, Integer>{ }