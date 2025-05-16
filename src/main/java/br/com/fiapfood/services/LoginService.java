
package br.com.fiapfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.impl.LoginRepository;
import br.com.fiapfood.services.exceptions.LoginSemAcessoException;
import br.com.fiapfood.utils.MensagensUtil;

@Service
public class LoginService {

	@Autowired
	private LoginRepository loginRepository;
	
	public String validar(LoginRecordRequest dados) {
		try {
			loginRepository.buscarPorMatriculaSenha(dados.matricula(), dados.senha());

			return "Acesso liberado";
		} catch (UsuarioNaoEncontradoException e) {
			throw new LoginSemAcessoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_SEM_PERMISSAO));
		}
	}

	public void trocarSenha(Integer id, String senha) {
		// Aqui poderia entrar a regra de negócio para validar que o usuário está ativo.
		LoginEntity login = loginRepository.buscarPorIdUsuario(id);
		
		login.atualizarSenha(senha);

		loginRepository.salvar(login);
	}
	
	public void atualizarMatricula(Integer id, String matricula) {
		LoginEntity login = loginRepository.buscarPorIdUsuario(id);
		
		login.atualizarMatricula(matricula);

		loginRepository.salvar(login);
	}
}