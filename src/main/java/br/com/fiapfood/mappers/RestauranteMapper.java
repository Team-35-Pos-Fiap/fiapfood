package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.EnderecoDomain;
import br.com.fiapfood.entities.domain.RestauranteDomain;
import br.com.fiapfood.entities.domain.UsuarioDomain;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.entities.record.response.PaginacaoRecordResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

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

	public static RestauranteDomain toDadosRestaurante(RestauranteRecordRequest dadosRestaurante, UsuarioDomain donoRestaurante) {
		return new RestauranteDomain(null,
				dadosRestaurante.nome(),
				EnderecoMapper.toDadosEndereco(dadosRestaurante.endereco()),
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				donoRestaurante);
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
	public static RestauranteRecordResponse toDadosRestauranteRecord(RestauranteDomain dadosRestaurante) {
		return new RestauranteRecordResponse(dadosRestaurante.getId(),
				dadosRestaurante.getNome(),
				EnderecoMapper.toDadosEnderecoRecord(dadosRestaurante.getEndereco()),
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				UsuarioMapper.toUsuarioRecord(dadosRestaurante.getDonoRestaurante()));
	}

	public static RestauranteRecordPaginacaoResponse toDadosRestauranteRecord(Page<RestauranteEntity> dados) {
		List<RestauranteRecordResponse> restaurantes = dados.toList()
				.stream()
				.map(u -> RestauranteMapper.toDadosRestaurante(u))
				.map(u -> RestauranteMapper.toDadosRestauranteRecord(u))
				.collect(Collectors.toList());

		PaginacaoRecordResponse dadosPaginacao = new PaginacaoRecordResponse(dados.getNumber() + 1,
				dados.getTotalPages(), Long.valueOf(dados.getTotalElements()).intValue());

		return new RestauranteRecordPaginacaoResponse(restaurantes, dadosPaginacao);
	}
}