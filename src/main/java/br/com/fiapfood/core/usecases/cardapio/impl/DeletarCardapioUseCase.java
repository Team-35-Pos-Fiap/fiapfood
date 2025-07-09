package br.com.fiapfood.core.usecases.cardapio.impl;

import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.gateways.interfaces.ICardapioGateway;
import br.com.fiapfood.core.usecases.cardapio.interfaces.IDeletarCardapioUseCase;

import java.util.UUID;

public class DeletarCardapioUseCase implements IDeletarCardapioUseCase {

    private final ICardapioGateway cardapioGateway;

    public DeletarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    @Override
    public void deletar(UUID id) {
        Cardapio cardapioExistente = cardapioGateway.buscarPorId(id);

        if(cardapioExistente == null) {
            throw new IllegalArgumentException("Cardápio não encontrado com o ID fornecido: " + id);
        }

        cardapioGateway.deletar(id);
      }
}