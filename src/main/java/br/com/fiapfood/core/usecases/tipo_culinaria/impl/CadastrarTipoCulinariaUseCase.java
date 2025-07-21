package br.com.fiapfood.core.usecases.tipo_culinaria.impl;

import br.com.fiapfood.core.entities.TipoCulinaria;
import br.com.fiapfood.core.exceptions.NomePerfilDuplicadoException;
import br.com.fiapfood.core.gateways.interfaces.ITipoCulinariaGateway;
import br.com.fiapfood.core.presenters.TipoCulinariaPresenter;
import br.com.fiapfood.core.usecases.tipo_culinaria.interfaces.ICadastrarTipoCulinariaUseCase;

public class CadastrarTipoCulinariaUseCase implements ICadastrarTipoCulinariaUseCase{

	private final ITipoCulinariaGateway tipoCulinariaGateway;
	
	public CadastrarTipoCulinariaUseCase(ITipoCulinariaGateway tipoCulinariaGateway) {
		this.tipoCulinariaGateway = tipoCulinariaGateway;
	}
	
	@Override
	public void cadastrar(final String nome) {
		validaNomeJaCadastrado(nome);
		
		salvar(toTipoCulinaria(nome));
	}

	private void salvar(TipoCulinaria tipoCulinaria) {
		tipoCulinariaGateway.salvar(TipoCulinariaPresenter.toTipoCulinariaDto(tipoCulinaria));
	}

	private void validaNomeJaCadastrado(final String nome) {
		if(tipoCulinariaGateway.nomeJaCadastrado(nome)) {
			throw new NomePerfilDuplicadoException("Já existe um tipo de culinária com o nome informado.");
		}
	}

	private TipoCulinaria toTipoCulinaria(final String nome) {
		return TipoCulinariaPresenter.toTipoCulinaria(nome);
	}
}