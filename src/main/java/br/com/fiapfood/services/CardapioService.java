
package br.com.fiapfood.services;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.domain.CardapioDomain;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.CardapioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.CardapioRecordResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.mappers.CardapioMapper;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.interfaces.ICardapioRepository;
import br.com.fiapfood.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.services.interfaces.ICardapioService;
import br.com.fiapfood.services.interfaces.IRestauranteService;
import br.com.fiapfood.utils.MensagensUtil;
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
		CardapioDomain cardapioDomain =  CardapioMapper.toDadosCardapio(cardapioRepository.buscarCardapioPorId(id));
		return CardapioMapper.toDadosCardapioRecord(cardapioDomain);
	}

	@Override
	public CardapioRecordPaginacaoResponse buscarTodos(Integer pagina) {
		return CardapioMapper.toDadosCardapioRecord(cardapioRepository.buscarTodosCardapios(pagina));
	}

	@Override
	public void cadastrar(CardapioRecordRequest cardapio) {
		CardapioDomain cardapioDomain =  CardapioMapper.toDadosCardapio(cardapio);
		CardapioEntity cardapioEntity = CardapioMapper.toDadosCardapio(cardapioDomain);

		salvar(cardapioEntity);
	}

	@Override
	public void atualizar(UUID id, CardapioRecordRequest cardapio) {
		CardapioEntity cardapioEntityExistente = cardapioRepository.buscarCardapioPorId(id);

		if(cardapioEntityExistente == null) {
			throw new IllegalArgumentException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_CARDAPIOS_NAO_ENCONTRADOS));
		}

		salvar(atualizarDadosCardapio(cardapioEntityExistente, cardapio));
	}

	@Override
	public void salvar(CardapioEntity cardapio) {
		cardapioRepository.salvarCardapio(cardapio);
	}

	@Override
	public void deletarCardapio(UUID id) {
		cardapioRepository.deletarCardapio(id);
	}

	private CardapioEntity atualizarDadosCardapio(CardapioEntity cardapioExistente, CardapioRecordRequest cardapioNovo) {
		cardapioExistente.atualizarDados(cardapioNovo.nome(),
				cardapioNovo.descricao(),
				cardapioNovo.preco(),
				cardapioNovo.disponivelApenasRestaurante(),
				cardapioNovo.fotoPrato());

		return cardapioExistente;
	}

}