package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.exceptions.AtualizacaoDonoRestauranteNaoPermitidaException;
import br.com.fiapfood.core.exceptions.AtualizacaoNomeRestauranteNaoPermitidaException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarDonoRestauranteUseCase;

public class AtualizarDonoRestauranteUseCase implements IAtualizarDonoRestauranteUseCase{
	private final IRestauranteGateway restauranteGateway;
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;
	private final String PERFIL_USUARIO_VALIDO = "Dono";

	public AtualizarDonoRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		this.restauranteGateway = restauranteGateway;
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void atualizar(final UUID id, final UUID idDono) {
		final Restaurante restaurante = buscarRestaurante(id);
		final Usuario usuario = buscarUsuario(idDono);

		validarUsuarioAtivo(usuario);
		validarPerfilUsuario(usuario);
		validarIdDonoRestaurante(idDono, restaurante);
		
		atualizarDono(restaurante, idDono);
		
		atualizar(restaurante);
	}

	private void validarUsuarioAtivo(final Usuario usuario) {
		if (!usuario.getIsAtivo()) {
			throw new AtualizacaoDonoRestauranteNaoPermitidaException("Não é possível atualizar o dono do restaurante pois ele se encontra inativo.");
		}
	}

	private void validarPerfilUsuario(final Usuario usuario) {
		final Perfil perfil = buscarPerfil(usuario.getIdPerfil());
		
		if(!perfil.getNome().equalsIgnoreCase(PERFIL_USUARIO_VALIDO)) {
			throw new AtualizacaoDonoRestauranteNaoPermitidaException("Não é possível atualizar o dono do restaurante pois ele não possui o perfil de dono.");
		}
	}
	
	private void validarIdDonoRestaurante(final UUID idDono, Restaurante restaurante) {
		if(restaurante.getIdDonoRestaurante().equals(idDono)){
			throw new AtualizacaoNomeRestauranteNaoPermitidaException("Não é possível atualizar o dono do restaurante, pois o identificação informada é igual a identificação atual do dono do restaurante.");
		}
	}
	
	private void atualizar(final Restaurante restaurante) {
		restauranteGateway.atualizar(RestaurantePresenter.toRestauranteDto(restaurante));
	}

	private void atualizarDono(Restaurante restaurante, UUID idDono) {
		restaurante.atualizarDono(idDono);		
	}
	
	private Restaurante buscarRestaurante(final UUID id) {
		return restauranteGateway.buscarPorId(id);
	}
	
	private Usuario buscarUsuario(UUID idDono) {
		return usuarioGateway.buscarPorId(idDono);
	}
	
	private Perfil buscarPerfil(Integer idPerfil) {
		return perfilGateway.buscarPorId(idPerfil);
	}
}