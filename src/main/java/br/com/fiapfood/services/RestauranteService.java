
package br.com.fiapfood.services;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.services.interfaces.IRestauranteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestauranteService implements IRestauranteService {

	private final IRestauranteRepository restauranteRepository;

	public RestauranteService(IRestauranteRepository restauranteRepository) {
		this.restauranteRepository = restauranteRepository;
	}


	@Override
	public RestauranteRecordResponse buscarPorId(UUID id) {
		return null;
	}

	@Override
	public void cadastrar(RestauranteRecordRequest restaurante) {

	}

	@Override
	public void salvar(RestauranteEntity restaurante) {

	}

	@Override
	public void atualizarEnderecoRestaurante(UUID id, EnderecoRecordRequest dadosEndereco) {

	}

	@Override
	public void atualizarDonoRestaurante(UUID id, UsuarioRecordRequest donoRestaurante) {

	}

	@Override
	public void deletarRestaurante(UUID id) {

	}
}