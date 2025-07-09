package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.DadosCardapioDto;

public interface ICadastrarCardapioUseCase {

    void cadastrar(DadosCardapioDto cardapio);
}