package br.com.fiapfood.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.fiapfood.repositories.interfaces.IUsuarioRepository;
import br.com.fiapfood.services.exceptions.PaginaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.jpa.IUsuarioJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class UsuarioRepository implements IUsuarioRepository {

	private final IUsuarioJpaRepository usuarioRepository;
	private final Integer QUANTIDADE_REGISTROS = 5;

	public UsuarioRepository(IUsuarioJpaRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UsuarioEntity recuperaDadosUsuarioPorId(UUID id) {
		return getUsuarioEntity(usuarioRepository.findById(id));
	}

	@Override
	public UsuarioEntity recuperaDadosUsuarioInativoPorId(UUID id) {
		return getUsuarioEntity(usuarioRepository.findByIdAndIsAtivoFalse(id));
	}

	@Override
	public UsuarioEntity recuperaDadosUsuarioAtivoPorId(UUID id) {
		return getUsuarioEntity(usuarioRepository.findByIdAndIsAtivoTrue(id));
	}

	@Override
	public UsuarioEntity recuperarDadosUsuarioPorIdLogin(UUID loginId){
		return getUsuarioEntity(usuarioRepository.findByIdLogin(loginId));
	}

	@Override
	public Page<UsuarioEntity> recuperaDadosUsuarios(final Integer pagina) {
		if (pagina == null || pagina < 1) {
			throw new PaginaInvalidaException();
		}
		Page<UsuarioEntity> usuarios = usuarioRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));
		
		if(usuarios.toList().isEmpty()) {		
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIOS_NAO_ENCONTRADOS));
		} else {
			return usuarios;
		}
	}

	@Override
	public void salvar(UsuarioEntity usuario) {
		usuarioRepository.save(usuario);	
	}
	
	private UsuarioEntity getUsuarioEntity(Optional<UsuarioEntity> dadosUsuario) {
		if(dadosUsuario.isPresent()) {		
			return dadosUsuario.get();
		} else {
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIO_NAO_ENCONTRADO));
		}
	}
}