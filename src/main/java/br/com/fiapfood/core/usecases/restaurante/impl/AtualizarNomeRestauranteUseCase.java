package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.exceptions.AtualizacaoNomeRestauranteNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarNomeRestauranteUseCase;

public class AtualizarNomeRestauranteUseCase implements IAtualizarNomeRestauranteUseCase {
	private final IRestauranteGateway restauranteGateway;

	public AtualizarNomeRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		this.restauranteGateway = restauranteGateway;
	}
	
	@Override
	public void atualizar(final UUID id, final String nome) {
		final Restaurante restaurante = buscarRestaurante(id);

		validarStatusRestaurante(restaurante);
		validarNome(nome, restaurante);

		atualizarNome(restaurante, nome);
		
		atualizar(restaurante);
	}
	
	private void atualizarNome(Restaurante restaurante, String nome) {
		 restaurante.atualizarNome(nome);
	}

	private void atualizar(final Restaurante restaurante) {
		restauranteGateway.atualizar(RestaurantePresenter.toRestauranteDto(restaurante));
	}
	
	private void validarStatusRestaurante(final Restaurante restaurante) {
		if (!restaurante.getIsAtivo()) {
			throw new AtualizacaoNomeRestauranteNaoPermitidaException("Não é possível alterar o nome do restaurante pois ele se encontra inativo.");
		} 
	}
	
	private void validarNome(final String nome, Restaurante restaurante) {
		if(restaurante.getNome().trim().equals(nome.trim())){
			throw new AtualizacaoNomeRestauranteNaoPermitidaException("Não é possível atualizar o nome do restaurante, pois o nome informado é igual ao nome atual do restaurante.");
		}
	}
	
	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
}