package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioDto;

import java.util.UUID;

public interface ICadastrarCardapioUseCase {

    void cadastrar(DadosCardapioDto cardapio);
}