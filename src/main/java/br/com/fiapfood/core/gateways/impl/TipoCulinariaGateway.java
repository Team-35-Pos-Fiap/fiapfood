package br.com.fiapfood.core.gateways.impl;

import java.util.List;

import br.com.fiapfood.core.entities.TipoCulinaria;
import br.com.fiapfood.core.entities.dto.tipo_culinaria.TipoCulinariaCoreDto;
import br.com.fiapfood.core.exceptions.TipoCulinariaInvalidoException;
import br.com.fiapfood.core.exceptions.TipoCulinariaNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.ITipoCulinariaGateway;
import br.com.fiapfood.core.presenters.TipoCulinariaPresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.ITipoCulinariaRepository;

public class TipoCulinariaGateway implements ITipoCulinariaGateway {

	private final ITipoCulinariaRepository tipoCulinariaRepository;
	
	public TipoCulinariaGateway(ITipoCulinariaRepository tipoCulinariaRepository) {
		this.tipoCulinariaRepository = tipoCulinariaRepository;
	}

	@Override
	public List<TipoCulinaria> buscarTodos() {
		final List<TipoCulinariaCoreDto> tiposCulinaria = tipoCulinariaRepository.buscarTodos();
		
		if(tiposCulinaria.size() > 0) {
			return TipoCulinariaPresenter.toListTipoCulinaria(tiposCulinaria);
		} else {
			throw new TipoCulinariaNaoEncontradoException("Não foi encontrado nenhum tipo de culinária na base de dados.");			
		}
	}
	
	@Override
	public TipoCulinaria buscarPorId(final Integer id) {
		final TipoCulinariaCoreDto TipoCulinaria = tipoCulinariaRepository.buscarPorId(id);
		
		if(TipoCulinaria != null) {
			return TipoCulinariaPresenter.toTipoCulinaria(TipoCulinaria);
		} else {
			throw new TipoCulinariaInvalidoException("Não foi encontrado nenhum tipo de culinária com o id informado.");			
		}
	}

	@Override
	public void salvar(final TipoCulinariaCoreDto tipoCulinaria) {
		tipoCulinariaRepository.salvar(TipoCulinariaPresenter.toTipoCulinariaEntity(tipoCulinaria));
	}

	@Override
	public boolean nomeJaCadastrado(final String nome) {
		return tipoCulinariaRepository.nomeJaCadastrado(nome);
	}
}