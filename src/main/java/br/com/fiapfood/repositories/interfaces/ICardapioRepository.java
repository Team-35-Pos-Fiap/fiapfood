package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.CardapioEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface ICardapioRepository {
    CardapioEntity buscarCardapioPorId(UUID id);
    Page<CardapioEntity> buscarTodosCardapios(Integer pagina);
    void salvarCardapio(CardapioEntity cardapio);
    void deletarCardapio(UUID id);
}
