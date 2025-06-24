package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;

import java.util.Optional;
import java.util.UUID;

public interface IRestauranteRepository {
    Optional<RestauranteEntity> buscarPorId(UUID id);
    void salvar(RestauranteEntity restaurante);
}
