package br.com.fiapfood.infraestructure.repositories.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.dto.login.LoginCoreDto;
import br.com.fiapfood.infraestructure.entities.LoginEntity;

public interface ILoginRepository {
    void salvar(LoginEntity login);
    LoginCoreDto buscarPorMatricula(String matricula);
    LoginCoreDto buscarPorMatriculaSenha(String matricula, String senha);
	LoginCoreDto buscarPorId(UUID id);
	boolean matriculaJaCadastrada(String matricula);
}