package br.com.fiapfood.infraestructure.repositories.interfaces;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.infraestructure.entities.CardapioEntity;

import java.util.Map;
import java.util.UUID;

public interface ICardapioRepository {
    CardapioDto buscarCardapioPorId(UUID id);
    Map<Class<?>, Object> buscarCardapioComPaginacao(Integer pagina);
    void salvarCardapio(CardapioEntity cardapio);
    void deletarCardapio(UUID id);
}
