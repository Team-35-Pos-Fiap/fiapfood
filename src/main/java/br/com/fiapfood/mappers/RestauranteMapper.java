package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.domain.RestauranteDomain;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;

public abstract class RestauranteMapper {

	// record -> domain -> entity
	
	// 1 - record -> domain
	public static RestauranteDomain toDadosRestaurante(RestauranteRecordRequest dadosRestaurante) {
		return new RestauranteDomain(null,
				dadosRestaurante.nome(),
				EnderecoMapper.toDadosEndereco(dadosRestaurante.endereco()),
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				UsuarioMapper.toUsuario(dadosRestaurante.donoRestaurante()));
	}
	
	// 2 - domain -> entity
	public static RestauranteEntity toDadosRestaurante(RestauranteDomain dadosRestaurante) {
		return new RestauranteEntity(dadosRestaurante.getId(),
				dadosRestaurante.getNome(),
				EnderecoMapper.toDadosEndereco(dadosRestaurante.getEndereco()),
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				UsuarioMapper.toUsuario(dadosRestaurante.getDonoRestaurante()));
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	public static RestauranteDomain toDadosRestaurante(RestauranteEntity dadosRestaurante) {
		return new RestauranteDomain(dadosRestaurante.getId(),
				dadosRestaurante.getNome(),
				EnderecoMapper.toDadosEndereco(dadosRestaurante.getEndereco()),
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				UsuarioMapper.toUsuario(dadosRestaurante.getDonoRestaurante()));
	}
	
	// 4 - domain -> record
	public static RestauranteRecordRequest toDadosRestauranteRecord(RestauranteDomain dadosRestaurante) {
		return new RestauranteRecordRequest(dadosRestaurante.getNome(),
				EnderecoMapper.toDadosEnderecoRecord(dadosRestaurante.getEndereco()),
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				UsuarioMapper.toUsuarioRecordRequest(dadosRestaurante.getDonoRestaurante()));
	}
}