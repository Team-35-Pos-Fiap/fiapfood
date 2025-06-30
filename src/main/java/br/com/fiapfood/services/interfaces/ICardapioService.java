package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.response.CardapioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.CardapioRecordResponse;

import java.util.UUID;

public interface ICardapioService {
    CardapioRecordResponse buscarPorId(UUID id);
    CardapioRecordPaginacaoResponse buscarTodos(Integer pagina);
    void cadastrar(CardapioRecordRequest cardapio);
    void atualizar(UUID id, CardapioRecordRequest cardapio);
    void salvar(CardapioEntity cardapio);
    void deletarCardapio(UUID id);
}
