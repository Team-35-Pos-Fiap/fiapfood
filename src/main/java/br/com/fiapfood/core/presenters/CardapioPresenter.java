package br.com.fiapfood.core.presenters;


import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.infraestructure.entities.CardapioEntity;

import java.util.List;

public class CardapioPresenter {

	public static CardapioDto toCardapioDto(CardapioEntity dadosCardapio) {
		return new CardapioDto(dadosCardapio.getId(),
				dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}
		
	public static Cardapio toCardapio(CardapioDto dadosCardapio) {
		return new Cardapio(dadosCardapio.id(),
				dadosCardapio.nome(),
				dadosCardapio.descricao(),
				dadosCardapio.preco(),
				dadosCardapio.disponivelApenasRestaurante(),
				dadosCardapio.fotoPrato());
	}

	public static CardapioDto toCardapioDto(Cardapio dadosCardapio) {
		return new CardapioDto(dadosCardapio.getId(),
				dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}
	
	public static DadosCardapioComPaginacaoDto toCardapioPaginacaoDto(List<CardapioDto> cardapios, PaginacaoDto paginacao) {
		return new DadosCardapioComPaginacaoDto(cardapios, paginacao);
	}

	public static List<CardapioDto> toListCardapioDto(List<CardapioEntity> cardapios) {
		return cardapios.stream().map(CardapioPresenter::toCardapioDto).toList();
	}
	
	public static List<Cardapio> toListCardapio(List<CardapioDto> cardapios) {
		return cardapios.stream().map(CardapioPresenter::toCardapio).toList();
	}

	public static CardapioEntity toCardapioEntity(CardapioDto dadosCardapio) {
		return new CardapioEntity(null,
				dadosCardapio.nome(),
				dadosCardapio.descricao(),
				dadosCardapio.preco(),
				dadosCardapio.disponivelApenasRestaurante(),
				dadosCardapio.fotoPrato());
	}

	public static CardapioEntity toCardapioEntity(DadosCardapioDto dadosCardapio) {
		return new CardapioEntity(null,
				dadosCardapio.nome(),
				dadosCardapio.descricao(),
				dadosCardapio.preco(),
				dadosCardapio.disponivelApenasRestaurante(),
				dadosCardapio.fotoPrato());
	}

	public static CardapioEntity toCardapioAtualizadoEntity(CardapioDto dadosCardapio) {
		return new CardapioEntity(dadosCardapio.id(),
				dadosCardapio.nome(),
				dadosCardapio.descricao(),
				dadosCardapio.preco(),
				dadosCardapio.disponivelApenasRestaurante(),
				dadosCardapio.fotoPrato());
	}

}