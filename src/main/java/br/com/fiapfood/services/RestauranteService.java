
package br.com.fiapfood.services;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.RestauranteDomain;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.mappers.EnderecoMapper;
import br.com.fiapfood.mappers.RestauranteMapper;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.services.interfaces.IRestauranteService;
import br.com.fiapfood.utils.MensagensUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RestauranteService implements IRestauranteService {

	private final IRestauranteRepository restauranteRepository;
	private final EnderecoService enderecoService;
	private final UsuarioService usuarioService;

	public RestauranteService(IRestauranteRepository restauranteRepository, EnderecoService enderecoService, UsuarioService usuarioService) {
		this.restauranteRepository = restauranteRepository;
		this.enderecoService = enderecoService;
		this.usuarioService = usuarioService;
	}

	@Override
	public RestauranteRecordResponse buscarPorId(UUID id) {
		RestauranteDomain restauranteDomain =  RestauranteMapper.toDadosRestaurante(restauranteRepository.buscarRestaurantePorId(id));
		return RestauranteMapper.toDadosRestauranteRecord(restauranteDomain);
	}

	@Override
	public RestauranteRecordPaginacaoResponse buscarTodos(Integer pagina) {
		return RestauranteMapper.toDadosRestauranteRecord(restauranteRepository.buscarTodosRestaurantes(pagina));
	}

	@Override
	public void cadastrar(RestauranteRecordRequest restaurante) {

		RestauranteDomain restauranteDomain =  RestauranteMapper.toDadosRestaurante(restaurante);
		RestauranteEntity restauranteEntity = RestauranteMapper.toDadosRestaurante(restauranteDomain);

		salvar(restauranteEntity);
	}

	@Override
	public void salvar(RestauranteEntity restaurante) {
		restauranteRepository.salvarRestaurante(restaurante);
	}

	@Override
	public void deletarRestaurante(UUID id) {
		restauranteRepository.deletarRestaurante(id);
	}

	@Override
	public void atualizarRestaurante(UUID id, RestauranteRecordRequest restaurante) {
		RestauranteEntity restauranteEntityExistente = restauranteRepository.buscarRestaurantePorId(id);

		if(restauranteEntityExistente == null) {
			throw new IllegalArgumentException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_RESTAURANTES_NAO_ENCONTRADOS));
		}

		salvar(atualizarDadosRestaurante(restauranteEntityExistente, restaurante));
	}

	@Override
	public void atualizarEnderecoRestaurante(UUID id, EnderecoRecordRequest dadosEndereco) {
		RestauranteEntity restaurante = restauranteRepository.buscarRestaurantePorId(id);

		enderecoService.atualizarEndereco(restaurante.getEndereco(), dadosEndereco);
	}

	@Override
	public void atualizarDonoRestaurante(UUID id, UsuarioRecordRequest donoRestaurante) {}

	private RestauranteEntity atualizarDadosRestaurante(RestauranteEntity restauranteExistente, RestauranteRecordRequest restauranteNovo) {
		restauranteExistente.atualizarDados(
			restauranteNovo.nome(), EnderecoMapper.toDadosEndereco(EnderecoMapper.toDadosEndereco(restauranteNovo.endereco())),
			restauranteNovo.tipoCozinha(),
			restauranteNovo.horarioFuncionamento(), UsuarioMapper.toUsuario(UsuarioMapper.toUsuario(restauranteNovo.donoRestaurante()))
		);

		return restauranteExistente;
	}
}