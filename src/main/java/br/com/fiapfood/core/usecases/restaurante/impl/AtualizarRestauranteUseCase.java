package br.com.fiapfood.core.usecases.restaurante.impl;

import br.com.fiapfood.core.entities.*;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.gateways.interfaces.*;
import br.com.fiapfood.core.presenters.*;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarRestauranteUseCase;
import br.com.fiapfood.infraestructure.entities.EnderecoEntity;
import br.com.fiapfood.infraestructure.entities.LoginEntity;
import br.com.fiapfood.infraestructure.entities.PerfilEntity;
import br.com.fiapfood.infraestructure.entities.RestauranteEntity;

import java.util.UUID;

public class AtualizarRestauranteUseCase implements IAtualizarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;
    private final IEnderecoGateway enderecoGateway;
    private final IUsuarioGateway usuarioGateway;
    private final IPerfilGateway perfilGateway;
    private final ILoginGateway loginGateway;

    public AtualizarRestauranteUseCase(IRestauranteGateway restauranteGateway,
                                       IEnderecoGateway enderecoGateway,
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
    public void atualizar(UUID id, DadosRestauranteDto restaurante) {
        Restaurante restauranteExistente = restauranteGateway.buscarPorId(id);

        if(restauranteExistente == null) {
            throw new IllegalArgumentException("Restaurante não encontrado com o ID fornecido: " + id);
        }

        Usuario usuario = usuarioGateway.buscarPorId(restaurante.donoRestaurante());

        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Dono do restaurante não encontrado com o ID fornecido.");
        }

        restauranteGateway.salvar(atualizarDadosRestaurante(restauranteExistente, restaurante));

    }

    private RestauranteDto atualizarDadosRestaurante(Restaurante restauranteExistente, DadosRestauranteDto restauranteNovo) {
        restauranteExistente.atualizarDados(restauranteNovo.nome(),
                restauranteNovo.endereco(),
                restauranteNovo.tipoCozinha(),
                restauranteNovo.horarioFuncionamento(),
                restauranteNovo.donoRestaurante());

        Usuario usuario = usuarioGateway.buscarPorId(restauranteExistente.getIdDonoRestaurante());
        Perfil perfil = perfilGateway.buscarPorId(usuario.getIdPerfil());
        Login login = loginGateway.buscarPorId(usuario.getIdLogin());
        Endereco endereco = enderecoGateway.buscarPorId(restauranteExistente.getIdEndereco());
        Endereco enderecoUsuario = enderecoGateway.buscarPorId(usuario.getIdEndereco());

        return RestaurantePresenter.toRestauranteDto(restauranteExistente, endereco, usuario,
                perfil, login, enderecoUsuario);
    }
}