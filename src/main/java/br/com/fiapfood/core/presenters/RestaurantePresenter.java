package br.com.fiapfood.core.presenters;

import br.com.fiapfood.core.entities.*;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.infraestructure.entities.*;


import java.util.List;

public class RestaurantePresenter {

	public static RestauranteDto toRestauranteDto(RestauranteEntity dadosRestaurante, EnderecoDto endereco, UsuarioDto usuario) {
		return new RestauranteDto(dadosRestaurante.getId(),
				dadosRestaurante.getNome(),
				endereco,
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				usuario);
	}

	public static RestauranteDto toRestauranteDto(DadosRestauranteDto dadosRestaurante, Endereco enderecoUsuario, UsuarioDto usuario) {
		return new RestauranteDto(null,
				dadosRestaurante.nome(),
				EnderecoPresenter.toEnderecoDto(enderecoUsuario),
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				usuario);
	}

	public static Restaurante toRestaurante(RestauranteDto dadosRestaurante) {
		return new Restaurante(dadosRestaurante.id(),
				dadosRestaurante.nome(),
				dadosRestaurante.endereco().id(),
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				dadosRestaurante.donoRestaurante().id());
	}



	public static RestauranteDto toRestauranteDto(Restaurante dadosRestaurante, Endereco endereco,
												  Usuario usuario, Perfil perfil, Login login, Endereco enderecoUsuario) {
		return new RestauranteDto(dadosRestaurante.getId(),
				dadosRestaurante.getNome(),
				EnderecoPresenter.toEnderecoDto(endereco),
				dadosRestaurante.getTipoCozinha(),
				dadosRestaurante.getHorarioFuncionamento(),
				UsuarioPresenter.toUsuarioDto(usuario,
						perfil,
						login,
						enderecoUsuario));
	}

	public static DadosRestauranteComPaginacaoDto toRestaurantePaginacaoDto(List<RestauranteDto> restaurantes, PaginacaoDto paginacao) {
		return new DadosRestauranteComPaginacaoDto(restaurantes, paginacao);
	}

	public static List<RestauranteDto> toListRestauranteDto(List<RestauranteEntity> restaurantes) {
		return restaurantes.stream().map(u -> RestaurantePresenter.toRestauranteDto(u,
																		EnderecoPresenter.toEnderecoDto(u.getEndereco()),
																		UsuarioPresenter.toUsuarioDto(u.getDonoRestaurante(),
																				PerfilPresenter.toPerfilDto(u.getDonoRestaurante().getPerfil()),
																				LoginPresenter.toLoginDto(u.getDonoRestaurante().getDadosLogin()),
																				EnderecoPresenter.toEnderecoDto(u.getDonoRestaurante().getDadosEndereco())
																		))).toList();
	}
	
	public static List<Restaurante> toListRestaurante(List<RestauranteDto> restaurantes) {
		return restaurantes.stream().map(RestaurantePresenter::toRestaurante).toList();
	}

	public static RestauranteEntity toRestauranteEntity(RestauranteDto dadosRestaurante, EnderecoEntity endereco, UsuarioEntity usuario) {
		return new RestauranteEntity(null,
				dadosRestaurante.nome(),
				endereco,
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				usuario);
	}

	public static RestauranteEntity toRestauranteAtualizadoEntity(RestauranteDto dadosRestaurante, EnderecoEntity endereco, UsuarioEntity usuario) {
		return new RestauranteEntity(dadosRestaurante.id(),
				dadosRestaurante.nome(),
				endereco,
				dadosRestaurante.tipoCozinha(),
				dadosRestaurante.horarioFuncionamento(),
				usuario);
	}
}