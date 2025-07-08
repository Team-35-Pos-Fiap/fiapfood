package br.com.fiapfood.core.controllers.impl;

import br.com.fiapfood.core.controllers.interfaces.ILoginCoreController;
import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarMatriculaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarSenhaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IValidarLoginUseCase;

import java.util.UUID;

public class RestauranteCoreController implements IRestauranteCoreController {

	@Override
	public DadosRestauranteComPaginacaoDto buscarTodos(Integer pagina) {
		return null;
	}

	@Override
	public RestauranteDto buscarPorId(UUID id) {
		return null;
	}

	@Override
	public void cadastrar(DadosRestauranteDto restaurante) {

	}

	@Override
	public void atualizarRestaurante(UUID id, DadosRestauranteDto request) {

	}

	@Override
	public void atualizarEnderecoRestaurante(UUID id, DadosEnderecoDto dadosEndereco) {

	}

	@Override
	public void deletarRestaurante(UUID id) {

	}
}