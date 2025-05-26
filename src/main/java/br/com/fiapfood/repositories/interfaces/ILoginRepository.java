package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.LoginEntity;

import java.util.UUID;

public interface ILoginRepository {
    LoginEntity buscarPorId(UUID id);
    LoginEntity buscarPorMatriculaSenha(String matricula, String senha);
    LoginEntity buscarPorMatricula(String matricula);
    void salvar(LoginEntity login);
    boolean matriculaJaCadastrada(String matricula);
}
