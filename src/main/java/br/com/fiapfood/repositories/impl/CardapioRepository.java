package br.com.fiapfood.repositories.impl;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.repositories.interfaces.ICardapioRepository;
import br.com.fiapfood.repositories.interfaces.jpa.ICardapioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CardapioRepository implements ICardapioRepository {

	private final ICardapioJpaRepository cardapioRepository;

	public CardapioRepository(ICardapioJpaRepository cardapioRepository) {

		this.cardapioRepository = cardapioRepository;
	}

	@Override
	public Optional<CardapioEntity> buscarPorId(UUID id) {

		return cardapioRepository.findById(id);
	}

	@Override
	public void salvar(CardapioEntity cardapio) {

		cardapioRepository.save(cardapio);
	}
}