package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.CardapioDomain;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.response.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CardapioMapper {

	// record -> domain -> entity
	
	// 1 - record -> domain
	public static CardapioDomain toDadosCardapio(CardapioRecordRequest dadosCardapioRecord) {
		return new CardapioDomain(null,
				dadosCardapioRecord.nome(),
				dadosCardapioRecord.descricao(),
				dadosCardapioRecord.preco(),
				dadosCardapioRecord.disponivelApenasRestaurante(),
				dadosCardapioRecord.fotoPrato());
	}
	
	// 2 - domain -> entity
	public static CardapioEntity toDadosCardapio(CardapioDomain dadosCardapio) {
		return new CardapioEntity(dadosCardapio.getId(),
				dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	public static CardapioDomain toDadosCardapio(CardapioEntity dadosCardapio) {
		return new CardapioDomain(dadosCardapio.getId(),
				dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}
	
	// 4 - domain -> record
	public static CardapioRecordResponse toDadosCardapioRecord(CardapioDomain dadosCardapio) {
		return new CardapioRecordResponse(dadosCardapio.getId(),
				dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}

	public static CardapioRecordPaginacaoResponse toDadosCardapioRecord(Page<CardapioEntity> dados) {
		List<CardapioRecordResponse> cardapios = dados.toList()
				.stream()
				.map(u -> CardapioMapper.toDadosCardapio(u))
				.map(u -> CardapioMapper.toDadosCardapioRecord(u))
				.collect(Collectors.toList());

		PaginacaoRecordResponse dadosPaginacao = new PaginacaoRecordResponse(dados.getNumber() + 1,
				dados.getTotalPages(), Long.valueOf(dados.getTotalElements()).intValue());

		return new CardapioRecordPaginacaoResponse(cardapios, dadosPaginacao);
	}
}