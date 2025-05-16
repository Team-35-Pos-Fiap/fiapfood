package br.com.fiapfood.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.EnderecoEntity;

public interface IEnderecoRepository extends JpaRepository<EnderecoEntity, Integer>{ }