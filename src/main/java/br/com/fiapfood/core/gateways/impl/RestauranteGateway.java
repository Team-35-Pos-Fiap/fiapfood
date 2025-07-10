package br.com.fiapfood.core.gateways.impl;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.core.entities.dto.UsuarioDto;
import br.com.fiapfood.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.*;
import br.com.fiapfood.infraestructure.entities.UsuarioEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.IRestauranteRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RestauranteGateway implements IRestauranteGateway {

	private final IRestauranteRepository restauranteRepository;

	public RestauranteGateway(IRestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	@Override
	public Restaurante buscarPorId(final UUID id) {
		final RestauranteDto restaurante = restauranteRepository.buscarRestaurantePorId(id);
		
		if(restaurante != null) {
			return RestaurantePresenter.toRestaurante(restaurante);
		} else {
			throw new RestauranteNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.");
		}
	}

	@Override
	public Map<Class<?>, Object> buscarRestaurantesComPaginacao(final Integer pagina) {
		final Map<Class<?>, Object> dados = restauranteRepository.buscarRestauranteComPaginacao(pagina);
		
		if(dados.get(List.class) != null) {
			return dados;
		} else {
			throw new RestauranteNaoEncontradoException("Não foi encontrado nenhum usuário com o login informado.");
		}
	}

	@Override
	public void salvar(final RestauranteDto restaurante) {
		restauranteRepository.salvarRestaurante(RestaurantePresenter
				.toRestauranteAtualizadoEntity(restaurante,
						EnderecoPresenter.toEnderecoEntity(restaurante.endereco()),
						UsuarioPresenter.toUsuarioEntity(restaurante.donoRestaurante(),
								EnderecoPresenter.toEnderecoEntity(restaurante.donoRestaurante().endereco()),
								PerfilPresenter.toPerfilEntity(restaurante.donoRestaurante().perfil()),
								LoginPresenter.toLoginEntity(restaurante.donoRestaurante().login())
						)));
	}

	@Override
	public void deletar(UUID id) {
		restauranteRepository.deletarRestaurante(id);
	}
}