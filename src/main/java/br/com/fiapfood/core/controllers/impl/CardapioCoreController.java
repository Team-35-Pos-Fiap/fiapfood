package br.com.fiapfood.core.controllers.impl;

import br.com.fiapfood.core.controllers.interfaces.ICardapioCoreController;
import br.com.fiapfood.core.controllers.interfaces.ILoginCoreController;
import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioComPaginacaoDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioDto;
import br.com.fiapfood.core.entities.dto.LoginDto;
import br.com.fiapfood.core.usecases.cardapio.interfaces.*;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarMatriculaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarSenhaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IValidarLoginUseCase;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class CardapioCoreController implements ICardapioCoreController {

	private final IAtualizarCardapioUseCase atualizarCardapioUseCase;
	private final IBuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase;
	private final IBuscarTodosCardapioUseCase buscarTodosCardapioUseCase;
	private final ICadastrarCardapioUseCase cadastrarCardapioUseCase;
	private final IDeletarCardapioUseCase deletarCardapioUseCase;
	private final ISalvarCardapioUseCase salvarCardapioUseCase;

	public CardapioCoreController(IAtualizarCardapioUseCase atualizarCardapioUseCase,
								   IBuscarCardapioPorIdUseCase buscarCardapioPorIdUseCase,
								   IBuscarTodosCardapioUseCase buscarTodosCardapioUseCase,
								   ICadastrarCardapioUseCase cadastrarCardapioUseCase,
								   IDeletarCardapioUseCase deletarCardapioUseCase,
								   ISalvarCardapioUseCase salvarCardapioUseCase) {
		this.atualizarCardapioUseCase = atualizarCardapioUseCase;
		this.buscarCardapioPorIdUseCase = buscarCardapioPorIdUseCase;
		this.buscarTodosCardapioUseCase = buscarTodosCardapioUseCase;
		this.cadastrarCardapioUseCase = cadastrarCardapioUseCase;
		this.deletarCardapioUseCase = deletarCardapioUseCase;
		this.salvarCardapioUseCase = salvarCardapioUseCase;
	}

	@Override
	public void cadastrar(DadosCardapioDto cardapio) {
		cadastrarCardapioUseCase.cadastrar(cardapio);
	}

	@Override
	public void atualizar(UUID id, DadosCardapioDto request) {
		atualizarCardapioUseCase.atualizar(id, request);
	}

	@Override
	public void deletarCardapio(UUID id) {
		deletarCardapioUseCase.deletar(id);
	}

	@Override
	public DadosCardapioComPaginacaoDto buscarTodos(Integer pagina) {
		return buscarTodosCardapioUseCase.buscarTodos(pagina);
	}

	@Override
	public CardapioDto buscarPorId(UUID id) {
		return buscarCardapioPorIdUseCase.buscarPorId(id);
	}
}