package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.EnderecoEntity;

import java.util.Optional;
import java.util.UUID;

public interface IEnderecoRepository {
    Optional<EnderecoEntity> buscarPorId(UUID id);
    void salvar(EnderecoEntity endereco);
}
