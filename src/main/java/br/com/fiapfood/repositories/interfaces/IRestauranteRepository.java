package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface IRestauranteRepository {
    RestauranteEntity buscarRestaurantePorId(UUID id);
    Page<RestauranteEntity> buscarTodosRestaurantes(Integer pagina);
    void salvarRestaurante(RestauranteEntity restaurante);
    void deletarRestaurante(UUID id);
}
