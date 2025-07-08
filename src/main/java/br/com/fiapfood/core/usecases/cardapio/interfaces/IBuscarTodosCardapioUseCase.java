package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioComPaginacaoDto;

import java.util.UUID;

public interface IBuscarTodosCardapioUseCase {

    DadosCardapioComPaginacaoDto buscarTodos(Integer pagina);
}