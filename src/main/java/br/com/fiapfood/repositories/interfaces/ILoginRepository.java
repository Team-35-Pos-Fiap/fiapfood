package br.com.fiapfood.repositories.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiapfood.entities.db.UsuarioEntity;

public interface ILoginRepository extends JpaRepository<LoginEntity, Integer> {
	Optional<LoginEntity> findByMatriculaAndSenha(String matricula, String senha);
}