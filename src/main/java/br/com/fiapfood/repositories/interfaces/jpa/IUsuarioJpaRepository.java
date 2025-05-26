package br.com.fiapfood.repositories.interfaces.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.UsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {
	Optional<UsuarioEntity> findByIdAndIsAtivoTrue(UUID id);

	Optional<UsuarioEntity> findByIdAndIsAtivoFalse(UUID id);

	@Query("SELECT u FROM UsuarioEntity u INNER JOIN u.dadosLogin dl WHERE dl.id = :id AND u.isAtivo = true")
	Optional<UsuarioEntity> findByIdLogin(@Param("id") UUID loginId);

	boolean existsByEmail(String email);
}