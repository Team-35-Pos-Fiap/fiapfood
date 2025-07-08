package br.com.fiapfood.core.controllers.interfaces;

import br.com.fiapfood.core.entities.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface ICardapioCoreController {

	void cadastrar(DadosCardapioDto cardapio);

	void atualizar(UUID id, DadosCardapioDto request);

	void deletarCardapio(UUID id);

	DadosCardapioComPaginacaoDto buscarTodos(Integer pagina);

	CardapioDto buscarPorId(UUID id);
}