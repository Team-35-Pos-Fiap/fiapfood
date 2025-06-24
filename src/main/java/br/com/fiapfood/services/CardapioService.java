
package br.com.fiapfood.services;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.CardapioRecordResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.repositories.interfaces.ICardapioRepository;
import br.com.fiapfood.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.services.interfaces.ICardapioService;
import br.com.fiapfood.services.interfaces.IRestauranteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardapioService implements ICardapioService {

	private final ICardapioRepository cardapioRepository;

	public CardapioService(ICardapioRepository cardapioRepository) {
		this.cardapioRepository = cardapioRepository;
	}

	@Override
	public CardapioRecordResponse buscarPorId(UUID id) {
		return null;
	}

	@Override
	public void cadastrar(CardapioRecordRequest cardapio) {

	}

	@Override
	public void salvar(CardapioEntity cardapio) {

	}

	@Override
	public void deletarCardapio(UUID id) {

	}
}