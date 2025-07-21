package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.TipoCulinaria;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.restaurante.DadosRestauranteCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioResumidoCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IEnderecoGateway;
import br.com.fiapfood.core.gateways.interfaces.ILoginGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.gateways.interfaces.ITipoCulinariaGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarRestaurantePorId;

public class BuscarRestaurantePorIdUseCase implements IBuscarRestaurantePorId {
	private final IRestauranteGateway restauranteGateway;
	private final IUsuarioGateway usuarioGateway;
	private final IEnderecoGateway enderecoGateway;
	private final ILoginGateway loginGateway;
	private final ITipoCulinariaGateway tipoCulinariaGateway;
		
	public BuscarRestaurantePorIdUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, 
								   		 IEnderecoGateway enderecoGateway, ILoginGateway loginGateway, 
								   		 ITipoCulinariaGateway tipoCulinariaGateway) {
		this.restauranteGateway = restauranteGateway;
		this.usuarioGateway = usuarioGateway;
		this.enderecoGateway = enderecoGateway;
		this.loginGateway = loginGateway;
		this.tipoCulinariaGateway = tipoCulinariaGateway;
	}
	
	@Override
	public DadosRestauranteCoreDto buscar(UUID id) {
		return toDadosRestauranteOutputDto(buscarPorId(id));
	}
	
	private DadosRestauranteCoreDto toDadosRestauranteOutputDto(Restaurante restaurante) {
		return RestaurantePresenter.toRestauranteDto(restaurante, 
													 buscarEndereco(restaurante.getIdEndereco()), 
													 buscarUsuario(restaurante.getIdDonoRestaurante()),
													 buscarTipoCulinaria(restaurante.getIdTipoCulinaria()),
													 restaurante.getAtendimentos());
	}
	
	private Restaurante buscarPorId(UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private TipoCulinaria buscarTipoCulinaria(Integer idTipoCulinaria) {
		return tipoCulinariaGateway.buscarPorId(idTipoCulinaria);
	}

	private DadosUsuarioResumidoCoreDto buscarUsuario(UUID id) {
		Usuario usuario = buscarUsuarioPorId(id);
		
		return UsuarioPresenter.toUsuarioOutputDto(usuario, buscarLogin(usuario.getIdLogin()));
	}
	
	private Login buscarLogin(final UUID idLogin) {
		return loginGateway.buscarPorId(idLogin);
	}
	
	private Endereco buscarEndereco(final UUID idEndereco) {
		return enderecoGateway.buscarPorId(idEndereco);
	}
	
	private Usuario buscarUsuarioPorId(final UUID idUsuario) {
		return usuarioGateway.buscarPorId(idUsuario);
	}
}