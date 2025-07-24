package br.com.fiapfood.core.usecases.usuario.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.exceptions.AtualizacaoStatusUsuarioNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.usuario.interfaces.IReativarUsuarioUseCase;

public class ReativarUsuarioUseCase implements IReativarUsuarioUseCase {
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;

	public ReativarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void reativar(final UUID id) {
		final Usuario usuario = buscarUsuario(id);
		
		validarUsuario(usuario);
		
		reativar(usuario);
		
		salvar(usuario);
	}

	private void reativar(Usuario usuario) {
		usuario.reativar();
	}

	private void validarUsuario(final Usuario usuario) {
		if (usuario.getIsAtivo()) {
			throw new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível reativar um usuário pois ele já se encontra ativo.");
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