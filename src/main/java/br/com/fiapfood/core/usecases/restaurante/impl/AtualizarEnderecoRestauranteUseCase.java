package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.endereco.DadosEnderecoCoreDto;
import br.com.fiapfood.core.exceptions.AtualizacaoEnderecoRestauranteNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IEnderecoGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.EnderecoPresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarEnderecoRestauranteUseCase;

public class AtualizarEnderecoRestauranteUseCase implements IAtualizarEnderecoRestauranteUseCase {
	private final IRestauranteGateway restauranteGateway;
	private final IEnderecoGateway enderecoGateway;

	public AtualizarEnderecoRestauranteUseCase(IRestauranteGateway restauranteGateway, IEnderecoGateway enderecoGateway) {
		this.restauranteGateway = restauranteGateway;
		this.enderecoGateway = enderecoGateway;
	}
	
	@Override
	public void atualizar(final UUID id, final DadosEnderecoCoreDto dadosEndereco) {
		final Restaurante restaurante = buscarRestaurante(id);

		validarStatusRestaurante(restaurante);
		
		final Endereco endereco = buscarEndereco(restaurante.getIdEndereco());
		
		atualizarEndereco(endereco, dadosEndereco);
		
		salvar(endereco);
	}

	private void atualizarEndereco(Endereco endereco, DadosEnderecoCoreDto dadosEndereco) {
		endereco.atualizarDados(dadosEndereco.endereco(), 
								dadosEndereco.cidade(), 
								dadosEndereco.bairro(), 
								dadosEndereco.estado(), 
								dadosEndereco.numero(), 
								dadosEndereco.cep(), 
								dadosEndereco.complemento());		
	}

	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private void validarStatusRestaurante(final Restaurante restaurante) {
		if (!restaurante.getIsAtivo()) {
			throw new AtualizacaoEnderecoRestauranteNaoPermitidaException("Não é possível alterar o endereço do restaurante pois ele se encontra inativo.");
		} 
	}

	private void salvar(final Endereco endereco) {
		enderecoGateway.salvar(EnderecoPresenter.toEnderecoDto(endereco));
	}

	private Endereco buscarEndereco(final UUID idEndereco) {
		return enderecoGateway.buscarPorId(idEndereco);
	}
}