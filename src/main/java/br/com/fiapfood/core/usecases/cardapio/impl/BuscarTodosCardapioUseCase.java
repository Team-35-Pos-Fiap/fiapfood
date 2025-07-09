package br.com.fiapfood.core.usecases.cardapio.impl;

import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.presenters.CardapioPresenter;
import br.com.fiapfood.core.usecases.cardapio.interfaces.IBuscarTodosCardapioUseCase;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BuscarTodosCardapioUseCase implements IBuscarTodosCardapioUseCase {

    private final ICardapioGateway cardapioGateway;

    public BuscarTodosCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    @Override
    public DadosCardapioComPaginacaoDto buscarTodos(final Integer pagina) {
        final Map<Class<?>, Object> dados = cardapioGateway.buscarCardapiosComPaginacao(pagina);

        final List<Cardapio> cardapios = toList(getListDto(dados));

        return CardapioPresenter.toCardapioPaginacaoDto(toListDto(cardapios), (PaginacaoDto)dados.get(PaginacaoDto.class));
    }

    @SuppressWarnings("unchecked")
    private List<CardapioDto> getListDto(final Map<Class<?>, Object> dados) {
        return (List<CardapioDto>) dados.get(List.class);
    }

    private List<Cardapio> toList(final List<CardapioDto> cardapios) {
        return CardapioPresenter.toListCardapio(cardapios);
    }

    private List<CardapioDto> toListDto(final List<Cardapio> cardapios) {
        return cardapios.stream().map(CardapioPresenter::toCardapioDto).toList();
    }
}