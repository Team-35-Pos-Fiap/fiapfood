package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.infraestructure.entities.CardapioEntity;

import java.util.UUID;

public interface IDeletarCardapioUseCase {

    void deletar(UUID id);
}