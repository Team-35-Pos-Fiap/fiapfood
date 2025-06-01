package br.com.fiapfood.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.domain.PerfilDomain;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;

@Component
public abstract class PerfilMapper {
	
	// record -> domain -> entity
	
	// 1 - record -> domain
	public static PerfilDomain toPerfil(Integer idPerfilAcesso) {
		return new PerfilDomain(idPerfilAcesso, null);
	}

	// 2 - domain -> entity
	
	public static PerfilEntity toPerfil(PerfilDomain perfil) {
		return new PerfilEntity(perfil.getId(), perfil.getNome());
	}
	
	// entity -> domain -> record
	
	// 3 - entity -> domain
	
	public static PerfilDomain toPerfil(PerfilEntity perfil) {
		return new PerfilDomain(perfil.getId(), perfil.getNome());
	}
	
	// 4 - domain -> record
	
	public static PerfilRecordResponse toPerfilRecord(PerfilDomain perfil) {
		return new PerfilRecordResponse(perfil.getId(), perfil.getNome());
	}

	public static List<PerfilRecordResponse> toPerfilRecord(List<PerfilEntity> dados) {
		List<PerfilRecordResponse> perfis = dados.stream()
				.map(PerfilMapper::toPerfil)
				.map(PerfilMapper::toPerfilRecord)
				.toList();

		return perfis;
	}
}