package br.com.fiapfood.core.gateways.impl;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.restaurante.DadosRestauranteDto;
import br.com.fiapfood.core.entities.dto.restaurante.RestaurantePaginacaoInputDto;
import br.com.fiapfood.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.IRestauranteRepository;

public class RestauranteGateway implements IRestauranteGateway{

	private final IRestauranteRepository restauranteRepository;
	
	public RestauranteGateway(IRestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}
	
	@Override
	public Restaurante buscarPorId(UUID id) {
		DadosRestauranteDto restaurante = restauranteRepository.buscarPorId(id);
		
		if(restaurante != null) {		 
			return RestaurantePresenter.toRestaurante(restaurante);
		} else {
			throw new RestauranteNaoEncontradoException("Não foi encontrado nenhum restaurante com o id informado.");
		}
	}

	@Override
	public RestaurantePaginacaoInputDto buscarTodos(Integer pagina) {
		RestaurantePaginacaoInputDto dados = restauranteRepository.buscarRestaurantesComPaginacao(pagina);
		
		if(dados != null) {
			return dados;
		} else {
			throw new RestauranteNaoEncontradoException("Não foram encontrados restaurantes na base de dados para a página informada.");
		}
	}

	@Override
	public void atualizar(DadosRestauranteDto restaurante) {
		restauranteRepository.salvarRestaurante(RestaurantePresenter.toRestauranteEntity(restaurante));		
	}

	@Override
	public void cadastrar(DadosRestauranteDto restaurante, EnderecoCoreDto endereco, List<AtendimentoCoreDto> atendimento) {
		restauranteRepository.salvarRestaurante(RestaurantePresenter.toRestauranteEntity(restaurante, endereco, atendimento));		
	}

	@Override
	public void atualizarAtendimentos(DadosRestauranteDto restaurante) {
		restauranteRepository.salvarRestaurante(RestaurantePresenter.toRestauranteComAtendimentosEntity(restaurante));		
	}
}