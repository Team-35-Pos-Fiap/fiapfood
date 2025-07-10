package br.com.fiapfood.core.usecases.restaurante.impl;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IDeletarRestauranteUseCase;

import java.util.UUID;

public class DeletarRestauranteUseCase implements IDeletarRestauranteUseCase {

    private final IRestauranteGateway restauranteGateway;

    public DeletarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    @Override
    public void deletar(UUID id) {
        Restaurante restauranteExistente = restauranteGateway.buscarPorId(id);

        if(restauranteExistente == null) {
            throw new IllegalArgumentException("Restaurante não encontrado com o ID fornecido: " + id);
        }

        restauranteGateway.deletar(id);
      }
}