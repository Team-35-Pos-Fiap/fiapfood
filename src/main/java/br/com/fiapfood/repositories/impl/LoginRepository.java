package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.repositories.interfaces.ILoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.jpa.ILoginJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class LoginRepository implements ILoginRepository {

    private final ILoginJpaRepository loginRepository;

	public LoginRepository(ILoginJpaRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Override
	public LoginEntity buscarPorId(UUID id) {
		return getLoginEntity(loginRepository.findById(id));
	}

	@Override
	public LoginEntity buscarPorMatriculaSenha(String matricula, String senha) {
		return getLoginEntity(loginRepository.findByMatriculaAndSenha(matricula, senha));
	}

	@Override
	public LoginEntity buscarPorMatricula(String matricula) {
		return getLoginEntity(loginRepository.findByMatricula(matricula));
	}

	@Override
	public void salvar(LoginEntity login) {
		loginRepository.save(login);		
	}

	@Override
	public boolean matriculaJaCadastrada(String matricula) {
		return loginRepository.existsByMatricula(matricula);
	}

	private LoginEntity getLoginEntity(Optional<LoginEntity> dadosLogin) {
		if (dadosLogin.isPresent()) {
			return dadosLogin.get();
		} else {
			throw new LoginNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
		}
	}
}
