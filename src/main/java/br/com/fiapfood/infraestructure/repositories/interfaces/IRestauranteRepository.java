package br.com.fiapfood.infraestructure.repositories.interfaces;

import br.com.fiapfood.infraestructure.entities.RestauranteEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IRestauranteRepository {
    RestauranteEntity buscarRestaurantePorId(UUID id);
    Page<RestauranteEntity> buscarTodosRestaurantes(Integer pagina);
    void salvarRestaurante(RestauranteEntity restaurante);
    void deletarRestaurante(UUID id);
}
