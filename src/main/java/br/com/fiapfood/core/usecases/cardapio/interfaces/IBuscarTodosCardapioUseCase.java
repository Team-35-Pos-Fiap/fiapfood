package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.DadosCardapioComPaginacaoDto;

public interface IBuscarTodosCardapioUseCase {

    DadosCardapioComPaginacaoDto buscarTodos(Integer pagina);
}