package br.com.fiapfood.core.gateways.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;

public interface IEnderecoGateway {
	Endereco buscarPorId(UUID id);
	void salvar(EnderecoCoreDto endereco);
}