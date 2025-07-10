package br.com.fiapfood.core.usecases.restaurante.interfaces;

import br.com.fiapfood.core.entities.dto.DadosRestauranteComPaginacaoDto;

public interface IBuscarTodosRestauranteUseCase {

    DadosRestauranteComPaginacaoDto buscarTodos(Integer pagina);
}