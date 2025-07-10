package br.com.fiapfood.core.usecases.restaurante.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.DadosRestauranteComPaginacaoDto;
import br.com.fiapfood.core.entities.dto.PaginacaoDto;
import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.core.gateways.interfaces.*;
import br.com.fiapfood.core.presenters.*;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarTodosRestauranteUseCase;
import br.com.fiapfood.infraestructure.entities.RestauranteEntity;

public class BuscarTodosRestaurantesUseCase implements IBuscarTodosRestauranteUseCase{
    private final IRestauranteGateway restauranteGateway;
    private final IUsuarioGateway usuarioGateway;
    private final IPerfilGateway perfilGateway;
    private final ILoginGateway loginGateway;
    private final IEnderecoGateway enderecoGateway;

    public BuscarTodosRestaurantesUseCase(IRestauranteGateway restauranteGateway,
                                          IUsuarioGateway usuarioGateway,
                                          IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
        this.perfilGateway = perfilGateway;
        this.loginGateway = loginGateway;
        this.enderecoGateway = enderecoGateway;
    }

    @Override
    public DadosRestauranteComPaginacaoDto buscarTodos(final Integer pagina) {

        final Map<Class<?>, Object> dados = buscarRestaurantesComPaginacao(pagina);

        return RestaurantePresenter.toRestaurantePaginacaoDto(getListDto(dados), (PaginacaoDto)dados.get(PaginacaoDto.class));
    }

    private Map<Class<?>, Object> buscarRestaurantesComPaginacao(final Integer pagina) {
        return  restauranteGateway.buscarRestaurantesComPaginacao(pagina);
    }

    @SuppressWarnings("unchecked")
    private List<RestauranteDto> getListDto(final Map<Class<?>, Object> dados) {
        return (List<RestauranteDto>) dados.get(List.class);
    }

    private Perfil buscarPerfil(final Integer idPerfil) {
        return perfilGateway.buscarPorId(idPerfil);
    }

    private Login buscarLogin(final UUID idLogin) {
        return loginGateway.buscarPorId(idLogin);
    }

    private Endereco buscarEndereco(final UUID idEndereco) {
        return enderecoGateway.buscarPorId(idEndereco);
    }
}