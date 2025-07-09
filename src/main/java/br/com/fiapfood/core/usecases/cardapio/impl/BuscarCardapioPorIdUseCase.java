package br.com.fiapfood.core.usecases.cardapio.impl;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.core.usecases.cardapio.interfaces.IBuscarCardapioPorIdUseCase;

import java.util.UUID;

public class BuscarCardapioPorIdUseCase implements IBuscarCardapioPorIdUseCase {

    private final ICardapioGateway cardapioGateway;

    public BuscarCardapioPorIdUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    @Override
    public CardapioDto buscarPorId(UUID id) {
        return  CardapioPresenter.toCardapioDto(cardapioGateway.buscarPorId(id));
    }
}