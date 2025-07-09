package br.com.fiapfood.core.usecases.cardapio.impl;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioDto;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.core.usecases.cardapio.interfaces.ICadastrarCardapioUseCase;
import br.com.fiapfood.infraestructure.entities.CardapioEntity;

public class CadastrarCardapioUseCase implements ICadastrarCardapioUseCase {

    private final ICardapioGateway cardapioGateway;

    public CadastrarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    @Override
    public void cadastrar(DadosCardapioDto cardapio) {

        CardapioEntity cardapioEntity =  CardapioPresenter.toCardapioEntity(cardapio);
        CardapioDto cardapioDto = CardapioPresenter.toCardapioDto(cardapioEntity);

        cardapioGateway.salvar(cardapioDto);
    }
}