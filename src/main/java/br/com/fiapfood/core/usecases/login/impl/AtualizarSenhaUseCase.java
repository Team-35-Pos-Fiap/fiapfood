package br.com.fiapfood.core.usecases.login.impl;

import br.com.fiapfood.core.gateways.interfaces.ILoginGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarSenhaUseCase;

public class AtualizarSenhaUseCase implements IAtualizarSenhaUseCase {

    private final IUsuarioGateway usuarioGateway;
    private final ILoginGateway loginGateway;

    public AtualizarSenhaUseCase(ILoginGateway loginGateway, IUsuarioGateway usuarioGateway) {
        this.loginGateway = loginGateway;
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void atualizar(String matricula, String senha) {

    }
}
