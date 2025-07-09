package br.com.fiapfood.core.gateways.interfaces;

import br.com.fiapfood.core.entities.Cardapio;
import br.com.fiapfood.core.entities.dto.CardapioDto;

import java.util.Map;
import java.util.UUID;

public interface ICardapioGateway {

	Cardapio buscarPorId(UUID id);
	Map<Class<?>, Object> buscarCardapiosComPaginacao(Integer pagina);
	void salvar(CardapioDto cardapioDto);
	void deletar(UUID id);
}