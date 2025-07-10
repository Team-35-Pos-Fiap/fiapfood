package br.com.fiapfood.core.usecases.restaurante.interfaces;

import br.com.fiapfood.core.entities.dto.DadosRestauranteDto;

import java.util.UUID;

public interface IAtualizarRestauranteUseCase {

    void atualizar(UUID id, DadosRestauranteDto restaurante);
}