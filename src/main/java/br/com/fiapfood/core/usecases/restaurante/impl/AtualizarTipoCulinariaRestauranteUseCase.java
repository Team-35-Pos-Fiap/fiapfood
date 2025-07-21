package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.exceptions.AtualizacaoTipoCulinariaRestauranteNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarTipoCulinariaRestauranteUseCase;

public class AtualizarTipoCulinariaRestauranteUseCase implements IAtualizarTipoCulinariaRestauranteUseCase{
	private final IRestauranteGateway restauranteGateway;
	
	public AtualizarTipoCulinariaRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		this.restauranteGateway = restauranteGateway;
	}

	@Override
	public void atualizar(final UUID id, final Integer idTipoCulinaria) {
		final Restaurante restaurante = buscarRestaurante(id);

		validarStatusRestaurante(restaurante);
		validarTipoCulinaria(idTipoCulinaria, restaurante);
			
		atualizarTipoCulinaria(restaurante, idTipoCulinaria);
		
		atualizar(restaurante);
	}

	private void validarStatusRestaurante(final Restaurante restaurante) {
		if (!restaurante.getIsAtivo()) {
			throw new AtualizacaoTipoCulinariaRestauranteNaoPermitidaException("Não é possível alterar o tipo de culinária do restaurante pois ele se encontra inativo.");
		} 
	}

	private void validarTipoCulinaria(final Integer idTipoCulinaria, final Restaurante restaurante) {
		if(restaurante.getIdTipoCulinaria().equals(idTipoCulinaria)){
			throw new AtualizacaoTipoCulinariaRestauranteNaoPermitidaException("Não é possível atualizar o tipo de culínaia do restaurante, pois o identificação informada é igual a identificação atual do tipo de culinária do restaurante.");
		}
	}
	
	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private void atualizar(final Restaurante restaurante) {
		restauranteGateway.atualizar(RestaurantePresenter.toRestauranteDto(restaurante));
	}
	
	private void atualizarTipoCulinaria(final Restaurante restaurante, final Integer idTipoCulinaria) {
		restaurante.atualizarTipoCulinaria(idTipoCulinaria);	
	}
}