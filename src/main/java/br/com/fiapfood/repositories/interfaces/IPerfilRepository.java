package br.com.fiapfood.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.PerfilEntity;

public interface IPerfilRepository extends JpaRepository<PerfilEntity, Integer> { }
