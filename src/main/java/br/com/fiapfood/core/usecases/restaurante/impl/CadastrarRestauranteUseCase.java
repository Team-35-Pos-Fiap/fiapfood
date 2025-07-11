package br.com.fiapfood.core.usecases.restaurante.impl;

import br.com.fiapfood.core.entities.*;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.gateways.interfaces.*;
import br.com.fiapfood.core.presenters.*;
import br.com.fiapfood.core.usecases.restaurante.interfaces.ICadastrarRestauranteUseCase;
import br.com.fiapfood.infraestructure.entities.*;

public class CadastrarRestauranteUseCase implements ICadastrarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;
    private final IEnderecoGateway enderecoGateway;
    private final IUsuarioGateway usuarioGateway;
    private final IPerfilGateway perfilGateway;
    private final ILoginGateway loginGateway;

    public CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway, IEnderecoGateway enderecoGateway,
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
    public void cadastrar(DadosRestauranteDto restaurante) {

        Usuario usuario = usuarioGateway.buscarPorId(restaurante.donoRestaurante());

        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário não encontrado com o ID fornecido.");
        }

        Perfil perfil = perfilGateway.buscarPorId(usuario.getIdPerfil());

        Login login = loginGateway.buscarPorId(usuario.getIdLogin());

        Endereco enderecoUsuario = enderecoGateway.buscarPorId(usuario.getIdEndereco());

        UsuarioDto usuarioDto = UsuarioPresenter.toUsuarioDto(usuario, perfil, login, enderecoUsuario);

        RestauranteDto dadosRestaurante = RestaurantePresenter.toRestauranteDto(restaurante, enderecoUsuario,
                usuarioDto);

        restauranteGateway.salvar(dadosRestaurante);
    }
}