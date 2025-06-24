package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.CardapioEntity;
import br.com.fiapfood.entities.domain.CardapioDomain;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;

public abstract class CardapioMapper {

	// record -> domain -> entity
	
	// 1 - record -> domain
	public static CardapioDomain toDadosCardapio(CardapioRecordRequest dadosCardapioRecord) {
		return new CardapioDomain(null,
				dadosCardapioRecord.nome(),
				dadosCardapioRecord.descricao(),
				dadosCardapioRecord.preco(),
				dadosCardapioRecord.DisponivelApenasRestaurante(),
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
	public static CardapioRecordRequest toDadosCardapioRecord(CardapioDomain dadosCardapio) {
		return new CardapioRecordRequest(dadosCardapio.getNome(),
				dadosCardapio.getDescricao(),
				dadosCardapio.getPreco(),
				dadosCardapio.getDisponivelApenasRestaurante(),
				dadosCardapio.getFotoPrato());
	}
}