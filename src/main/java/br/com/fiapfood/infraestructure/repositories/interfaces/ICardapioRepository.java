package br.com.fiapfood.infraestructure.repositories.interfaces;

import br.com.fiapfood.infraestructure.entities.CardapioEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ICardapioRepository {
    CardapioEntity buscarCardapioPorId(UUID id);
    Page<CardapioEntity> buscarTodosCardapios(Integer pagina);
    void salvarCardapio(CardapioEntity cardapio);
    void deletarCardapio(UUID id);
}
