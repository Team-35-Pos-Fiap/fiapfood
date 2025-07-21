package br.com.fiapfood.core.controllers.impl;

import java.util.UUID;

import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.presenters.AtendimentoPresenter;
import br.com.fiapfood.core.presenters.EnderecoPresenter;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IAdicionarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IAtualizarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IExcluirAtendimentoUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarDonoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarEnderecoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarNomeRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarTipoCulinariaRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarRestaurantePorId;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarTodosRestaurantesUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.ICadastrarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IInativarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IReativarRestauranteUseCase;
import br.com.fiapfood.infraestructure.controllers.request.atendimento.AtendimentoDto;
import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.CadastrarRestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestaurantePaginacaoDto;

public class RestauranteCoreController implements IRestauranteCoreController {
	private final IBuscarRestaurantePorId buscarRestaurantePorId;
	private final IBuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase;
	private final ICadastrarRestauranteUseCase cadastrarRestauranteUseCase;
	private final IReativarRestauranteUseCase reativarRestauranteUseCase;
	private final IInativarRestauranteUseCase inativarRestauranteUseCase;
	private final IAtualizarDonoRestauranteUseCase atualizarDonoRestauranteUseCase;
	private final IAtualizarEnderecoRestauranteUseCase atualizarEnderecoRestauranteUseCase;
	private final IAtualizarNomeRestauranteUseCase atualizarNomeRestauranteUseCase;
	private final IAtualizarTipoCulinariaRestauranteUseCase atualizarTipoCulinariaRestauranteUseCase;
	private final IAtualizarAtendimentoUseCase atualizarAtendimentoUseCase;
	private final IAdicionarAtendimentoUseCase adicionarAtendimentoUseCase;
	private final IExcluirAtendimentoUseCase excluirAtendimentoUseCase;

	public RestauranteCoreController(IBuscarRestaurantePorId buscarRestaurantePorId, IBuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase,
									 ICadastrarRestauranteUseCase cadastrarRestauranteUseCase, IReativarRestauranteUseCase reativarRestauranteUseCase,
									 IInativarRestauranteUseCase inativarRestauranteUseCase, IAtualizarDonoRestauranteUseCase atualizarDonoRestauranteUseCase,
									 IAtualizarEnderecoRestauranteUseCase atualizarEnderecoRestauranteUseCase, IAtualizarNomeRestauranteUseCase atualizarNomeRestauranteUseCase,
									 IAtualizarTipoCulinariaRestauranteUseCase atualizarTipoCulinariaRestauranteUseCase,
									 IAtualizarAtendimentoUseCase atualizarAtendimentoUseCase, IAdicionarAtendimentoUseCase adicionarAtendimentoUseCase,
									 IExcluirAtendimentoUseCase excluirAtendimentoUseCase) {
		this.buscarRestaurantePorId = buscarRestaurantePorId;
		this.buscarTodosRestaurantesUseCase = buscarTodosRestaurantesUseCase; 
		this.cadastrarRestauranteUseCase = cadastrarRestauranteUseCase;
		this.reativarRestauranteUseCase = reativarRestauranteUseCase;
		this.inativarRestauranteUseCase = inativarRestauranteUseCase;
		this.atualizarDonoRestauranteUseCase = atualizarDonoRestauranteUseCase;
		this.atualizarEnderecoRestauranteUseCase = atualizarEnderecoRestauranteUseCase;
		this.atualizarNomeRestauranteUseCase = atualizarNomeRestauranteUseCase;
		this.atualizarTipoCulinariaRestauranteUseCase = atualizarTipoCulinariaRestauranteUseCase;
		this.atualizarAtendimentoUseCase = atualizarAtendimentoUseCase;
		this.adicionarAtendimentoUseCase = adicionarAtendimentoUseCase;
		this.excluirAtendimentoUseCase = excluirAtendimentoUseCase;
	}
	
	@Override
	public RestaurantePaginacaoDto buscarTodos(final Integer pagina) {
		return RestaurantePresenter.toListDadosRestauranteDto(buscarTodosRestaurantesUseCase.buscar(pagina));
	}

	@Override
	public RestauranteDto buscarPorId(final UUID id) {
		return RestaurantePresenter.toDadosRestauranteDto(buscarRestaurantePorId.buscar(id));
	}

	@Override
	public void cadastrar(CadastrarRestauranteDto restaurante) {
		cadastrarRestauranteUseCase.cadastrar(RestaurantePresenter.toCadastrarRestaurante(restaurante));
	}
	
	@Override
	public void inativar(final UUID id) {
		inativarRestauranteUseCase.inativar(id);
	}
	
	@Override
	public void reativar(final UUID id) {
		reativarRestauranteUseCase.reativar(id);
	}
	
	@Override
	public void atualizarDono(final UUID id, final UUID idDono) {
		atualizarDonoRestauranteUseCase.atualizar(id, idDono);
	}
	
	@Override
	public void atualizarNome(final UUID id, final String nome) {
		atualizarNomeRestauranteUseCase.atualizar(id, nome);
	}
	
	@Override
	public void atualizarEndereco(final UUID id, final DadosEnderecoDto endereco) {
		atualizarEnderecoRestauranteUseCase.atualizar(id, EnderecoPresenter.toEnderecoCoreDto(endereco));
	}
	
	@Override
	public void atualizarTipoCulinaria(final UUID id, final Integer idTipoCulinaria) {
		atualizarTipoCulinariaRestauranteUseCase.atualizar(id, idTipoCulinaria);
	}
	
	@Override
	public void atualizarAtendimento(final UUID id, final AtendimentoDto atendimento) {
		atualizarAtendimentoUseCase.atualizar(id, AtendimentoPresenter.toAtendimentoCoreDto(atendimento));
	}
	
	@Override
	public void adicionarAtendimento(final UUID id, final AtendimentoDto atendimento) {
		adicionarAtendimentoUseCase.adicionar(id, AtendimentoPresenter.toAtendimentoCoreDto(atendimento));
	}
	
	@Override
	public void excluirAtendimento(final UUID idRestaurante, final UUID idAtendimento) {
		excluirAtendimentoUseCase.excluir(idRestaurante, idAtendimento);
	}
}