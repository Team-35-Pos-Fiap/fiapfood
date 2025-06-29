package br.com.fiapfood.repositories.impl;

import java.util.List;
import java.util.Optional;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import org.springframework.stereotype.Repository;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IPerfilJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;

@Repository
public class PerfilRepository implements IPerfilRepository {

    private final IPerfilJpaRepository perfilRepository;

	public PerfilRepository(IPerfilJpaRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}

	@Override
	public PerfilEntity buscarPorId(Integer id) {
		return getPerfil(perfilRepository.findById(id));
	}

	@Override
	public List<PerfilEntity> buscarTodos() {
		return perfilRepository.findAll();
	}

	@Override
	public boolean idJaCadastrado(Integer id) {
		return perfilRepository.existsById(id);
	}

	private PerfilEntity getPerfil(Optional<PerfilEntity> dados) {
		if(dados.isPresent()) {
			return dados.get();
		} else {
			throw new PerfilNaoEncontradoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PERFIL_NAO_ENCONTRADO));
		}
	}
}
