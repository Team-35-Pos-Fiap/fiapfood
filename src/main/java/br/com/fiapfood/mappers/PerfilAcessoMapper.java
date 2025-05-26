package br.com.fiapfood.mappers;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.response.PaginacaoRecordResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.domain.PerfilDomain;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class PerfilAcessoMapper {
	
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
				.map(PerfilAcessoMapper::toPerfil)
				.map(PerfilAcessoMapper::toPerfilRecord)
				.toList();

		return perfis;
	}
}