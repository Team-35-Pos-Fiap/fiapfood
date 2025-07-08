package br.com.fiapfood.infraestructure.repositories.impl;

import br.com.fiapfood.core.exceptions.PaginaInvalidaException;
import br.com.fiapfood.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.infraestructure.entities.RestauranteEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.IRestauranteJpaRepository;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RestauranteRepository implements IRestauranteRepository {

	private final IRestauranteJpaRepository restauranteRepository;
	private final Integer QUANTIDADE_REGISTROS = 5;

	public RestauranteRepository(IRestauranteJpaRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}	

	@Override
	public RestauranteEntity buscarRestaurantePorId(UUID id) {
		return getRestauranteEntity(restauranteRepository.findById(id));
	}

	@Override
	public Page<RestauranteEntity> buscarTodosRestaurantes(Integer pagina) {
		if (pagina == null || pagina < 1) {
			throw new PaginaInvalidaException();
		}
		Page<RestauranteEntity> restaurantes = restauranteRepository.findAll(PageRequest.of(pagina - 1, QUANTIDADE_REGISTROS));

		if(restaurantes.toList().isEmpty()) {
		} else {
			return restaurantes;
		}
        return restaurantes;
    }

	@Override
	public void salvarRestaurante(RestauranteEntity restaurante) {
		restauranteRepository.save(restaurante);
	}

	@Override
	public void deletarRestaurante(UUID id) {
		restauranteRepository.deleteById(id);
	}

	private RestauranteEntity getRestauranteEntity(Optional<RestauranteEntity> dadosRestaurante) {
		if(dadosRestaurante.isPresent()) {
			return dadosRestaurante.get();
		} else {
			throw new UsuarioNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_RESTAURANTES_NAO_ENCONTRADOS));
		}
	}
}