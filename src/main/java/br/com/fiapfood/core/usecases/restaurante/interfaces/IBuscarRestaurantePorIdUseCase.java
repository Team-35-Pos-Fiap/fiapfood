package br.com.fiapfood.core.usecases.restaurante.interfaces;

import br.com.fiapfood.core.entities.dto.RestauranteDto;

import java.util.UUID;

public interface IBuscarRestaurantePorIdUseCase {

    RestauranteDto buscarPorId(UUID id);
}