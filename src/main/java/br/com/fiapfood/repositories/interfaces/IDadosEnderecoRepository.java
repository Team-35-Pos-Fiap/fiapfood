package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;

import java.util.Optional;
import java.util.UUID;

public interface IDadosEnderecoRepository {
    Optional<DadosEnderecoEntity> recuperaDadosEndereco(UUID idUsuario);
}
