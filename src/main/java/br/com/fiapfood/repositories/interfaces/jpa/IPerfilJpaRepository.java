package br.com.fiapfood.repositories.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.PerfilEntity;

public interface IPerfilJpaRepository extends JpaRepository<PerfilEntity, Integer> {
}
