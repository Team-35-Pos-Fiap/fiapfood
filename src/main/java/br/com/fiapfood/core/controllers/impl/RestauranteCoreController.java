package br.com.fiapfood.core.controllers.impl;


import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.usecases.restaurante.interfaces.*;

import java.util.UUID;

public class RestauranteCoreController implements IRestauranteCoreController {

	private final IAtualizarRestauranteUseCase atualizarRestauranteUseCase;
	private final IBuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase;
	private final IBuscarTodosRestauranteUseCase buscarTodosRestauranteUseCase;
	private final ICadastrarRestauranteUseCase cadastrarRestauranteUseCase;
	private final IDeletarRestauranteUseCase deletarRestauranteUseCase;

	public RestauranteCoreController(IAtualizarRestauranteUseCase atualizarRestauranteUseCase,
									  IBuscarRestaurantePorIdUseCase buscarRestaurantePorIdUseCase,
									  IBuscarTodosRestauranteUseCase buscarTodosRestauranteUseCase,
									  ICadastrarRestauranteUseCase cadastrarRestauranteUseCase,
									  IDeletarRestauranteUseCase deletarRestauranteUseCase) {
		this.atualizarRestauranteUseCase = atualizarRestauranteUseCase;
		this.buscarRestaurantePorIdUseCase = buscarRestaurantePorIdUseCase;
		this.buscarTodosRestauranteUseCase = buscarTodosRestauranteUseCase;
		this.cadastrarRestauranteUseCase = cadastrarRestauranteUseCase;
		this.deletarRestauranteUseCase = deletarRestauranteUseCase;
	}

	@Override
	public DadosRestauranteComPaginacaoDto buscarTodos(Integer pagina) {
		return buscarTodosRestauranteUseCase.buscarTodos(pagina);
	}

	@Override
	public RestauranteDto buscarPorId(UUID id) {
		return buscarRestaurantePorIdUseCase.buscarPorId(id);
	}

	@Override
	public void cadastrar(DadosRestauranteDto restaurante) {
		cadastrarRestauranteUseCase.cadastrar(restaurante);
	}

	@Override
	public void atualizarRestaurante(UUID id, DadosRestauranteDto request) {
		atualizarRestauranteUseCase.atualizar(id, request);
	}

	@Override
	public void atualizarEnderecoRestaurante(UUID id, DadosEnderecoDto dadosEndereco) {

	}

	@Override
	public void deletarRestaurante(UUID id) {
		deletarRestauranteUseCase.deletar(id);
	}
}