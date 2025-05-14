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
	private IUsuarioRepository usuarioRepository;
		
	private final Integer QUANTIDADE_REGISTROS = 5;
	
	public UsuarioDomain recuperaDadosUsuarioPorLoginSenha(String usuario, String senha) {
		return null;//return getUsuarioDomain(usuarioRepository.findUsuarioAtivoPorLoginSenha(usuario, senha));
	}

	public Optional<UsuarioEntity> recuperaDadosUsuarioPorLogin(String login) {
		return null;//return usuarioRepository.findByLoginAndIsAtivoTrue(login);
	}

	public UsuarioDomain recuperaDadosUsuarioPorId(Integer id) {
		return getUsuarioDomain(usuarioRepository.findById(id));
	}

	public UsuarioEntity recuperaDadosUsuarioInativoPorId(Integer id) {
		return getUsuarioEntity(usuarioRepository.findByIdAndIsAtivoFalse(id));
	}

	public UsuarioEntity recuperaDadosUsuarioAtivoPorId(Integer id) {
		return getUsuarioEntity(usuarioRepository.findByIdAndIsAtivoTrue(id));
	}
	
	public Page<UsuarioEntity> recuperaDadosUsuarios(final Integer pagina) {
		Page<UsuarioEntity> usuarios = usuarioRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));
		
		if(usuarios.toList().isEmpty()) {		
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIOS_NAO_ENCONTRADOS));
		} else {
			return usuarios;
		}
	}
	
	public void salvar(UsuarioEntity usuario) {
		usuarioRepository.save(usuario);	
	}
	
	protected UsuarioDomain getUsuarioDomain(Optional<UsuarioEntity> dadosUsuario) {
		if(dadosUsuario.isPresent()) {		
			return UsuarioMapper.toUsuarioDomain(dadosUsuario.get());
		} else {
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIO_NAO_ENCONTRADO));
		}
	}
	
	protected UsuarioEntity getUsuarioEntity(Optional<UsuarioEntity> dadosUsuario) {
		if(dadosUsuario.isPresent()) {		
			return dadosUsuario.get();
		} else {
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIO_NAO_ENCONTRADO));
		}
	}
}