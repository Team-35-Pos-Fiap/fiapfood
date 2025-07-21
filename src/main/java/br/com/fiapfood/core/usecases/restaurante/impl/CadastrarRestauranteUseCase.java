package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Atendimento;
import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.DadosEnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.restaurante.CadastrarRestauranteCoreDto;
import br.com.fiapfood.core.exceptions.CadastrarRestauranteNaoPermitidoException;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.AtendimentoPresenter;
import br.com.fiapfood.core.presenters.EnderecoPresenter;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.ICadastrarRestauranteUseCase;

public class CadastrarRestauranteUseCase implements ICadastrarRestauranteUseCase {

	private final IRestauranteGateway restauranteGateway;
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;
	private final String PERFIL_USUARIO_VALIDO = "Dono";

	public CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		this.restauranteGateway = restauranteGateway;
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
	}
	
	@Override
	public void cadastrar(CadastrarRestauranteCoreDto restauranteDto) {
		Restaurante restaurante = toRestaurante(restauranteDto);
		Usuario usuario = buscarUsuario(restaurante.getIdDonoRestaurante());
		
		validarUsuarioAtivo(usuario);
		validarPerfilUsuario(usuario);
	
		cadastrar(restaurante, toEndereco(restauranteDto.dadosEndereco()), toListAtendimento(restauranteDto.atendimentos()));
	}

	private void cadastrar(Restaurante restaurante, Endereco endereco, List<Atendimento> atendimentos) {
		restauranteGateway.cadastrar(RestaurantePresenter.toRestauranteDto(restaurante), 
				  				  	 EnderecoPresenter.toEnderecoDto(endereco),
				  				  	 AtendimentoPresenter.toListAtendimentoDto(atendimentos));		
	}

	private void validarPerfilUsuario(Usuario usuario) {
		Perfil perfil = buscarPerfil(usuario.getIdPerfil());
		
		if(!perfil.getNome().equalsIgnoreCase(PERFIL_USUARIO_VALIDO)) {
			throw new CadastrarRestauranteNaoPermitidoException("Não é possível cadastrar o restaurante pois o responsável não possui o perfil de dono.");
		}
	}

	private Usuario buscarUsuario(UUID idDono) {
		return usuarioGateway.buscarPorId(idDono);
	}
	
	private void validarUsuarioAtivo(final Usuario usuario) {
		if (!usuario.getIsAtivo()) {
			throw new CadastrarRestauranteNaoPermitidoException("Não é possível cadastrar o restaurante pois o responsável se encontra inativo.");
		}
	}
	
	private Endereco toEndereco(DadosEnderecoCoreDto dadosEndereco) {
		return EnderecoPresenter.toEndereco(dadosEndereco);
	}
	
	private Restaurante toRestaurante(CadastrarRestauranteCoreDto restaurante) {
		return RestaurantePresenter.toRestaurante(restaurante);
	}
	
	private Perfil buscarPerfil(Integer idPerfil) {
		return perfilGateway.buscarPorId(idPerfil);
	}
	
	private List<Atendimento> toListAtendimento(List<AtendimentoCoreDto> atendimentos) {
		return AtendimentoPresenter.toListAtendimento(atendimentos);
	}
}