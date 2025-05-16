package br.com.fiapfood.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiapfood.entities.db.LoginEntity;

public interface ILoginRepository extends JpaRepository<LoginEntity, Integer>{ 
	Optional<LoginEntity> findByMatriculaAndSenha(String matricula, String senha);
}