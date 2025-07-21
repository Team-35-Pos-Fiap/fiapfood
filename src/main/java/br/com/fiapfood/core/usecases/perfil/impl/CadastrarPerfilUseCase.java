package br.com.fiapfood.core.usecases.perfil.impl;

import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.exceptions.NomePerfilDuplicadoException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.presenters.PerfilPresenter;
import br.com.fiapfood.core.usecases.perfil.interfaces.ICadastrarPerfilUseCase;

public class CadastrarPerfilUseCase implements ICadastrarPerfilUseCase{

	private final IPerfilGateway perfilGateway;
	
	public CadastrarPerfilUseCase(IPerfilGateway perfilGateway) {
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void cadastrar(final String nome) {
		validaNomeJaCadastrado(nome);
		
		salvar(toPerfil(nome));
	}

	private void salvar(Perfil perfil) {
		perfilGateway.salvar(PerfilPresenter.toPerfilDto(perfil));		
	}

	private void validaNomeJaCadastrado(final String nome) {
		if(perfilGateway.nomeJaCadastrado(nome)) {
			throw new NomePerfilDuplicadoException("Já existe um perfil com o nome informado.");
		}
	}

	private Perfil toPerfil(final String nome) {
		return PerfilPresenter.toPerfil(nome);
	}
}