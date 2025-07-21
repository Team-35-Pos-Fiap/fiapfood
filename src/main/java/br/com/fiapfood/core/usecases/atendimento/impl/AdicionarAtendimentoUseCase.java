package br.com.fiapfood.core.usecases.atendimento.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Atendimento;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;
import br.com.fiapfood.core.exceptions.AtualizacaoStatusRestauranteNaoPermitidaException;
import br.com.fiapfood.core.exceptions.atendimento.DiaAtendimentoRestauranteInvalidoException;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.presenters.AtendimentoPresenter;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IAdicionarAtendimentoUseCase;

public class AdicionarAtendimentoUseCase implements IAdicionarAtendimentoUseCase {

	private final IRestauranteGateway restauranteGateway;

	public AdicionarAtendimentoUseCase(IRestauranteGateway restauranteGateway) {
		this.restauranteGateway = restauranteGateway;
	}
	
	@Override
	public void adicionar(UUID id, AtendimentoCoreDto atendimentoDto) {
		final Restaurante restaurante = buscarRestaurante(id);

		validarStatusRestaurante(restaurante);
		validaDiaAtendimento(restaurante.getAtendimentos(), atendimentoDto);
		
		final Atendimento atendimento = toAtendimento(atendimentoDto);
		
		atualizarAtendimentosRestaurante(restaurante, atendimento);
		atualizar(restaurante);
	}

	private void atualizarAtendimentosRestaurante(Restaurante restaurante, Atendimento atendimento) {
		atualizarAtendimento(restaurante, atendimento);
	}

	private void atualizarAtendimento(Restaurante restaurante, Atendimento atendimento) {		
		List<Atendimento> atendimentos = getAtendimentos(restaurante);
		
		atualizarAtendimentoNaLista(atendimentos, atendimento);
		
		limparAtendimentos(restaurante);
		
		associarAtendimentos(restaurante, atendimentos); 
	}

	private List<Atendimento> getAtendimentos(Restaurante restaurante) {
		return new ArrayList<>(restaurante.getAtendimentos());	
	}
	
	private void associarAtendimentos(Restaurante restaurante, List<Atendimento> atendimentos) {
		restaurante.getAtendimentos().addAll(atendimentos);
	}

	private void limparAtendimentos(Restaurante restaurante) {
		restaurante.limparAtendimentos();
	}

	private void atualizarAtendimentoNaLista(List<Atendimento> atendimentos, Atendimento atendimento) {
		atendimentos.add(atendimento);
	}

	private void validaDiaAtendimento(List<Atendimento> atendimentos, AtendimentoCoreDto atendimento) {
		if(atendimentos.stream().filter(a -> a.getDia().equals(atendimento.dia())).findAny().isPresent()) {
			throw new DiaAtendimentoRestauranteInvalidoException("Não é possível atualizar o atendimento, pois já existe um outro atendimento para o mesmo dia.");
		}
	}

	private void validarStatusRestaurante(final Restaurante restaurante) {
		if (!restaurante.getIsAtivo()) {
			throw new AtualizacaoStatusRestauranteNaoPermitidaException("Não é possível inativar o restaurante pois ele já se encontra inativo.");
		} 
	}

	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private void atualizar(final Restaurante restaurante) {
		restauranteGateway.atualizarAtendimentos(RestaurantePresenter.toRestauranteDto(restaurante));
	}
	
	private Atendimento toAtendimento(AtendimentoCoreDto atendimento) {
		return AtendimentoPresenter.toAtendimento(atendimento);
	}
}