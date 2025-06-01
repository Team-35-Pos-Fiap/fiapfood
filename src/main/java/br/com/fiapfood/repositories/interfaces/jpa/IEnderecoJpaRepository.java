package br.com.fiapfood.repositories.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.EnderecoEntity;

import java.util.UUID;

public interface IEnderecoJpaRepository extends JpaRepository<EnderecoEntity, UUID>{
}