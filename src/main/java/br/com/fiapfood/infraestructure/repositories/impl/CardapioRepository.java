package br.com.fiapfood.infraestructure.repositories.impl;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.PaginacaoDto;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.core.presenters.PaginacaoPresenter;
import br.com.fiapfood.core.utils.MapUtils;
import br.com.fiapfood.infraestructure.entities.CardapioEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.ICardapioRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.ICardapioJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CardapioRepository implements ICardapioRepository {

	private final ICardapioJpaRepository cardapioRepository;
	private final Integer QUANTIDADE_REGISTROS = 5;

	public CardapioRepository(ICardapioJpaRepository cardapioRepository) {

		this.cardapioRepository = cardapioRepository;
	}

	@Override
	public CardapioDto buscarCardapioPorId(UUID id) {

		final Optional<CardapioEntity> cardapio = cardapioRepository.findById(id);

		if(cardapio.isPresent()) {
			return CardapioPresenter.toCardapioDto(cardapio.get());
		} else {
			return null;
		}
	}

	@Override
	public Map<Class<?>, Object> buscarCardapioComPaginacao(final Integer pagina) {
		return getDados(cardapioRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS)));
	}

	private Map<Class<?>, Object> getDados(final Page<CardapioEntity> resultado) {
		MapUtils mapUtils = new MapUtils();

		mapUtils.adicionarItens(CardapioPresenter.toListCardapioDto(resultado.get().collect(Collectors.toList())), List.class);
		mapUtils.adicionarItens(PaginacaoPresenter.toDto(resultado.getNumber(), resultado.getTotalPages(), Long.valueOf(resultado.getTotalElements()).intValue()), PaginacaoDto.class);

		return mapUtils.getMap();
	}

	@Override
	public void salvarCardapio(CardapioEntity cardapio) {
		cardapioRepository.save(cardapio);
	}

	@Override
	public void deletarCardapio(UUID id) {
		cardapioRepository.deleteById(id);
	}
}