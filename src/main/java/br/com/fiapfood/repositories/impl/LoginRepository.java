package br.com.fiapfood.repositories.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.ILoginRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class LoginRepository {

	@Autowired
	ILoginRepository loginRepository;
	
	public LoginEntity buscarPorIdUsuario(Integer id) {
		return getLoginEntity(loginRepository.findById(id));
	}

	public LoginEntity buscarPorMatriculaSenha(String matricula, String senha) {
		return getLoginEntity(loginRepository.findByMatriculaAndSenha(matricula, senha));
	}

	public void salvar(LoginEntity login) {
		loginRepository.save(login);		
	}
	
	private LoginEntity getLoginEntity(Optional<LoginEntity> dadosLogin) {
		if (dadosLogin.isPresent()) {
			return dadosLogin.get();
		} else {
			throw new LoginNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
		}
	}
}
