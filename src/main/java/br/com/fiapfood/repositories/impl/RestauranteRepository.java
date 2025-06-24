package br.com.fiapfood.repositories.impl;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IRestauranteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RestauranteRepository implements IRestauranteRepository {

	private final IRestauranteJpaRepository restauranteRepository;

	public RestauranteRepository(IRestauranteJpaRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}

	@Override
	public Optional<RestauranteEntity> buscarPorId(UUID id) {
		return restauranteRepository.findById(id);
	}

	@Override
	public void salvar(RestauranteEntity restaurante) {
		restauranteRepository.save(restaurante);
	}
}