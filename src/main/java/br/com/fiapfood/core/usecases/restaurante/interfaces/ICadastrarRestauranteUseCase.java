package br.com.fiapfood.core.usecases.restaurante.interfaces;

import br.com.fiapfood.core.entities.dto.DadosRestauranteDto;

public interface ICadastrarRestauranteUseCase {

    void cadastrar(DadosRestauranteDto restaurante);
}