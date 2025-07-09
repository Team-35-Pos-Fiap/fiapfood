package br.com.fiapfood.core.usecases.cardapio.impl;

import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioDto;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.core.usecases.cardapio.interfaces.IAtualizarCardapioUseCase;

import java.util.UUID;

public class AtualizarCardapioUseCase implements IAtualizarCardapioUseCase {

    private final ICardapioGateway cardapioGateway;

    public AtualizarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    @Override
    public void atualizar(UUID id, DadosCardapioDto cardapio) {
        Cardapio cardapioExistente = cardapioGateway.buscarPorId(id);

        if(cardapioExistente == null) {
            throw new IllegalArgumentException("Cardápio não encontrado com o ID fornecido: " + id);
        }

        cardapioGateway.salvar(atualizarDadosCardapio(cardapioExistente, cardapio));

    }

    private CardapioDto atualizarDadosCardapio(Cardapio cardapioExistente, DadosCardapioDto cardapioNovo) {
        cardapioExistente.atualizarDados(cardapioNovo.nome(),
                cardapioNovo.descricao(),
                cardapioNovo.preco(),
                cardapioNovo.disponivelApenasRestaurante(),
                cardapioNovo.fotoPrato());

        return CardapioPresenter.toCardapioDto(cardapioExistente);
    }
}