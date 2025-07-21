package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.exceptions.AtualizacaoStatusRestauranteNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IReativarRestauranteUseCase;

public class ReativarRestauranteUseCase implements IReativarRestauranteUseCase {
	private final IRestauranteGateway restauranteGateway;

	public ReativarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		this.restauranteGateway = restauranteGateway;
	}
	
	@Override
	public void reativar(final UUID id) {
		final Restaurante restaurante = buscarRestaurante(id);

		validarStatusRestaurante(restaurante);
		
		reativar(restaurante);
		atualizar(restaurante);
	}

	private void validarStatusRestaurante(final Restaurante restaurante) {
		if (restaurante.getIsAtivo()) {
			throw new AtualizacaoStatusRestauranteNaoPermitidaException("Não é possível reativar o restaurante pois ele já se encontra ativo.");
		} 
	}

	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private void reativar(Restaurante restaurante) {		
		restaurante.reativar();
	}
	
	private void atualizar(final Restaurante restaurante) {
		restauranteGateway.atualizar(RestaurantePresenter.toRestauranteDto(restaurante));
	}
}