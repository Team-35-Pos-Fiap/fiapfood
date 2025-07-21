package br.com.fiapfood.infraestructure.repositories.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.infraestructure.entities.EnderecoEntity;

public interface IEnderecoRepository {
	EnderecoCoreDto buscarPorId(UUID id);
    void salvar(EnderecoEntity endereco);
}
