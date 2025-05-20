package br.com.fiapfood.repositories.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.UsuarioEntity;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
	Optional<UsuarioEntity> findByIdAndIsAtivoTrue(UUID id);

	Optional<UsuarioEntity> findByIdAndIsAtivoFalse(UUID id);
}