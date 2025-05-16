package br.com.fiapfood.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.UsuarioEntity;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
	Optional<UsuarioEntity> findByIdAndIsAtivoTrue(Integer id);

	Optional<UsuarioEntity> findByIdAndIsAtivoFalse(Integer id);
}