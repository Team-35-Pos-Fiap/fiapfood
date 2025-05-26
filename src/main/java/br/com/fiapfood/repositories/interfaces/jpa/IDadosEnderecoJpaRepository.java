package br.com.fiapfood.repositories.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;

import java.util.UUID;

public interface IDadosEnderecoJpaRepository extends JpaRepository<DadosEnderecoEntity, UUID>{ }