package br.com.fiapfood.infraestructure.repositories.interfaces;

import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.infraestructure.entities.RestauranteEntity;

import java.util.Map;
import java.util.UUID;

public interface IRestauranteRepository {
    RestauranteDto buscarRestaurantePorId(UUID id);
    Map<Class<?>, Object> buscarRestauranteComPaginacao(Integer pagina);
    void salvarRestaurante(RestauranteEntity restaurante);
    void deletarRestaurante(UUID id);
}
