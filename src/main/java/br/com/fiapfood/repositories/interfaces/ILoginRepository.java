package br.com.fiapfood.repositories.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.LoginEntity;

public interface ILoginRepository extends JpaRepository<LoginEntity, UUID>{
	Optional<LoginEntity> findByMatriculaAndSenha(String matricula, String senha);
}