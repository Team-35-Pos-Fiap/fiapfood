package br.com.fiapfood.core.usecases.usuario.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.exceptions.AtualizacaoStatusUsuarioNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.usuario.interfaces.IInativarUsuarioUseCase;

public class InativarUsuarioUseCase implements IInativarUsuarioUseCase {
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;

	public InativarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void inativar(final UUID id) {
		final Usuario usuario = buscarUsuario(id);
		
		validarUsuario(usuario);
		
		inativar(usuario);
		
		salvar(usuario);
	}

	private void inativar(Usuario usuario) {
		usuario.inativar();		
	}

	private void validarUsuario(final Usuario usuario) {
		if (!usuario.getIsAtivo()) {
			throw new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível inativar o usuário pois ele já se encontra inativo.");
		} 
	}

	private void salvar(final Usuario usuario) {
		usuarioGateway.salvar(UsuarioPresenter.toUsuarioDto(usuario, 
															buscarPerfil(usuario.getIdPerfil())));
	}
	
	private Usuario buscarUsuario(final UUID id) {
		return usuarioGateway.buscarPorId(id);
	}
	
	private Perfil buscarPerfil(final Integer idPerfil) {
		return perfilGateway.buscarPorId(idPerfil);
	}
}