package br.com.fiapfood.core.usecases.usuario.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.exceptions.usuario.AtualizacaoEmailUsuarioNaoPermitidoException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarEmailUsuarioUseCase;

public class AtualizarEmailUsuarioUseCase implements IAtualizarEmailUsuarioUseCase {
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;

	public AtualizarEmailUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void atualizar(final UUID id, final String email) {
		final Usuario usuario = buscarUsuario(id);
		
		validarUsuario(usuario);
		validarEmailExistente(email);
		validarEmail(usuario, email);
		
		atualizarEmail(usuario, email);
		
		salvar(usuario);
	}

	private void atualizarEmail(Usuario usuario, String email) {
		usuario.atualizarEmail(email);
	}

	private void validarUsuario(final Usuario usuario) {
		if (!usuario.getIsAtivo()) {
			throw new AtualizacaoEmailUsuarioNaoPermitidoException("Não é possível alterar o email de um usuário inativo.");
		} 
	}

	private void validarEmailExistente(final String email) {
		if(usuarioGateway.emailJaCadastrado(email)){
			throw new AtualizacaoEmailUsuarioNaoPermitidoException("Já existe um usuário com o email informado.");
		}
	}

	private void validarEmail(final Usuario usuario, final String email) {
		if(usuario.getEmail().equals(email)){
			throw new AtualizacaoEmailUsuarioNaoPermitidoException("Não é possível alterar o email do usuário, pois ele já é igual ao email atual.");
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