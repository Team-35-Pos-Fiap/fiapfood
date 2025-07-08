package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.DadosCardapioDto;

import java.util.UUID;

public interface IAtualizarCardapioUseCase {

    void atualizar(UUID id, DadosCardapioDto cardapio);
}