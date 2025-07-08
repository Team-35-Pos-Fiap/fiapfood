package br.com.fiapfood.core.controllers.interfaces;

import br.com.fiapfood.core.entities.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface IRestauranteCoreController {

	DadosRestauranteComPaginacaoDto buscarTodos(Integer pagina);

	RestauranteDto buscarPorId(UUID id);

	void cadastrar(DadosRestauranteDto restaurante);

	void atualizarRestaurante(UUID id, DadosRestauranteDto request);

	void atualizarEnderecoRestaurante(UUID id, DadosEnderecoDto dadosEndereco);

	void deletarRestaurante(UUID id);
}