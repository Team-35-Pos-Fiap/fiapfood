package br.com.fiapfood.core.usecases.usuario.impl;

import br.com.fiapfood.core.entities.dto.DadosEnderecoDto;
import br.com.fiapfood.core.gateways.interfaces.IEnderecoGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarEnderecoUsuarioUseCase;

import java.util.UUID;

public class AtualizarEnderecoUsuarioUseCase implements IAtualizarEnderecoUsuarioUseCase {
    private final IUsuarioGateway usuarioGateway;
    private final IEnderecoGateway enderecoGateway;

    public AtualizarEnderecoUsuarioUseCase(IUsuarioGateway usuarioGateway, IEnderecoGateway enderecoGateway) {
        this.usuarioGateway = usuarioGateway;
        this.enderecoGateway = enderecoGateway;
    }

    @Override
    public void atualizar(UUID id, DadosEnderecoDto dadosEndereco) {

    }
}
