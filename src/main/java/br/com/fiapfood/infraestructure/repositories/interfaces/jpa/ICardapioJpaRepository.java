package br.com.fiapfood.infraestructure.repositories.interfaces.jpa;

import br.com.fiapfood.infraestructure.entities.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICardapioJpaRepository extends JpaRepository<CardapioEntity, UUID> {
}