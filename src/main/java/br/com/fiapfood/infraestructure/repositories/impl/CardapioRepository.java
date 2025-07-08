package br.com.fiapfood.infraestructure.repositories.impl;

import br.com.fiapfood.core.exceptions.CardapioNaoEncontradoException;
import br.com.fiapfood.core.exceptions.PaginaInvalidaException;
import br.com.fiapfood.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.infraestructure.entities.CardapioEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.ICardapioRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.ICardapioJpaRepository;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CardapioRepository implements ICardapioRepository {

	private final ICardapioJpaRepository cardapioRepository;
	private final Integer QUANTIDADE_REGISTROS = 5;

	public CardapioRepository(ICardapioJpaRepository cardapioRepository) {

		this.cardapioRepository = cardapioRepository;
	}

	@Override
	public CardapioEntity buscarCardapioPorId(UUID id) {
		return getCardapioEntity(cardapioRepository.findById(id));
	}

	@Override
	public Page<CardapioEntity> buscarTodosCardapios(Integer pagina) {
		if (pagina == null || pagina < 1) {
			throw new PaginaInvalidaException();
		}
		Page<CardapioEntity> cardapios = cardapioRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));

		if(cardapios.toList().isEmpty()) {
			throw new CardapioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_CARDAPIOS_NAO_ENCONTRADOS));
		} else {
			return cardapios;
		}
	}

	@Override
	public void salvarCardapio(CardapioEntity cardapio) {
		cardapioRepository.save(cardapio);
	}

	@Override
	public void deletarCardapio(UUID id) {
		cardapioRepository.deleteById(id);
	}

	private CardapioEntity getCardapioEntity(Optional<CardapioEntity> dadosCardapio) {
		if(dadosCardapio.isPresent()) {
			return dadosCardapio.get();
		} else {
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_CARDAPIOS_NAO_ENCONTRADOS));
		}
	}
}