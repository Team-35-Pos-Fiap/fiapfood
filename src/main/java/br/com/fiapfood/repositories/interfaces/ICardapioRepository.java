package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.CardapioEntity;

import java.util.Optional;
import java.util.UUID;

public interface ICardapioRepository {
    Optional<CardapioEntity> buscarPorId(UUID id);
    void salvar(CardapioEntity cardapio);
}
