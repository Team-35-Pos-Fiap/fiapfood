package br.com.fiapfood.repositories.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.UsuarioDomain;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IUsuarioRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class UsuarioRepository {

	@Autowired
	private ILoginRepository loginRepository;
		
	public LoginEntity buscarPorIdUsuario(Integer id) {
		return getLoginEntity(loginRepository.findById(id));	
	}
	
	public void salvar(LoginEntity login) {
		usuarioRepository.save(login);	
	}
	
	protected LoginEntity getLoginEntity(Optional<LoginEntity> dadosLogin) {
		if(dadosLogin.isPresent()) {		
			return dadosLogin.get();
		} else {
			throw new LoginNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
		}
	}
}