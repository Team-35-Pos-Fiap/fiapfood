package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.record.request.LoginRecordRequest;

import java.util.UUID;

public interface ILoginService {

    String validar(LoginRecordRequest dados);
    void trocarSenha(String matricula, String senha);
//    void atualizarMatricula(UUID idUsuario, String matricula);
}
