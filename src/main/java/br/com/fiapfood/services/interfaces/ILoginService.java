package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.record.request.LoginRecordRequest;

public interface ILoginService {

    String validar(LoginRecordRequest dados);
    void trocarSenha(String matricula, String senha);
    boolean matriculaJaCadastrada(String matricula);
}
