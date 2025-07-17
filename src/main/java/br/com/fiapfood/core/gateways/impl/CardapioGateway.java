package br.com.fiapfood.core.gateways.impl;

import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.exceptions.CardapioNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.ICardapioRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CardapioGateway implements ICardapioGateway {

	private final ICardapioRepository cardapioRepository;

	public CardapioGateway(ICardapioRepository cardapioRepository) {
		this.cardapioRepository = cardapioRepository;
	}
	
	@Override
	public Cardapio buscarPorId(final UUID id) {
		final CardapioDto cardapio = cardapioRepository.buscarCardapioPorId(id);
		
		if(cardapio != null) {
			return CardapioPresenter.toCardapio(cardapio);
		} else {
			throw new CardapioNaoEncontradoException("Não foi encontrado nenhum cardapio com o id informado.");
		}
	}

	@Override
	public Map<Class<?>, Object> buscarCardapiosComPaginacao(final Integer pagina) {
		final Map<Class<?>, Object> dados = cardapioRepository.buscarCardapioComPaginacao(pagina);
		
		if(dados.get(List.class) != null) {
			return dados;
		} else {
			throw new CardapioNaoEncontradoException("Não foi encontrado nenhum cardapio com o login informado.");
		}
	}

	@Override
	public void salvar(final CardapioDto cardapio) {
		cardapioRepository.salvarCardapio(CardapioPresenter.toCardapioAtualizadoEntity(cardapio));
	}

	@Override
	public void deletar(final UUID id) {
		cardapioRepository.deletarCardapio(id);;
	}
}