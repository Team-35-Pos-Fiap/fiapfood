package br.com.fiapfood.core.usecases.restaurante.impl;

import br.com.fiapfood.core.entities.*;
import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.core.entities.dto.UsuarioDto;
import br.com.fiapfood.core.gateways.interfaces.*;
import br.com.fiapfood.core.presenters.RestaurantePresenter;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarRestaurantePorIdUseCase;
import br.com.fiapfood.infraestructure.entities.RestauranteEntity;

import java.util.UUID;

public class BuscarRestaurantePorIdUseCase implements IBuscarRestaurantePorIdUseCase {

    private final IRestauranteGateway restauranteGateway;
    private final IEnderecoGateway enderecoGateway;
    private final IUsuarioGateway usuarioGateway;
    private final IPerfilGateway perfilGateway;
    private final ILoginGateway loginGateway;

    public BuscarRestaurantePorIdUseCase(IRestauranteGateway restauranteGateway, IEnderecoGateway enderecoGateway,
                                         IUsuarioGateway usuarioGateway,
                                         IPerfilGateway perfilGateway,
                                         ILoginGateway loginGateway) {
        this.restauranteGateway = restauranteGateway;
        this.enderecoGateway = enderecoGateway;
        this.usuarioGateway = usuarioGateway;
        this.perfilGateway = perfilGateway;
        this.loginGateway = loginGateway;
    }

    @Override
    public RestauranteDto buscarPorId(UUID id) {
        Restaurante restaurante = restauranteGateway.buscarPorId(id);
        Endereco endereco = enderecoGateway.buscarPorId(restaurante.getIdEndereco());
        Usuario usuario = usuarioGateway.buscarPorId(restaurante.getIdDonoRestaurante());
        Perfil perfil = perfilGateway.buscarPorId(usuario.getIdPerfil());
        Login login = loginGateway.buscarPorId(usuario.getIdLogin());
        Endereco enderecoUsuario = enderecoGateway.buscarPorId(usuario.getIdEndereco());

        return  RestaurantePresenter.toRestauranteDto(restaurante, endereco, usuario,
                perfil, login, enderecoUsuario);
    }
}
