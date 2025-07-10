package br.com.fiapfood.core.gateways.interfaces;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.infraestructure.entities.UsuarioEntity;

import java.util.Map;
import java.util.UUID;

public interface IRestauranteGateway {
	Restaurante buscarPorId(UUID id);
	Map<Class<?>, Object> buscarRestaurantesComPaginacao(Integer pagina);
	void salvar(RestauranteDto restauranteDto);
	void deletar(UUID id);
}