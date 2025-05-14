package br.com.fiapfood.repositories.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class PerfilRepository {

	@Autowired
	IPerfilRepository perfilRepository;
	
	public PerfilEntity buscarPorId(Integer id) {
		return getPerfil(perfilRepository.findById(id));
	}
	
	private PerfilEntity getPerfil(Optional<PerfilEntity> dados) {
		if(dados.isPresent()) {
			return dados.get();
		} else {
			throw new PerfilNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PERFIL_NAO_ENCONTRADO));
		}
	}
}
