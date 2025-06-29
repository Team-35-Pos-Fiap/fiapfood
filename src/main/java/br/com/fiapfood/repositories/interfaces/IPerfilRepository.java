package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.PerfilEntity;

import java.util.List;

public interface IPerfilRepository {
    PerfilEntity buscarPorId(Integer id);
    List<PerfilEntity> buscarTodos();
    boolean idJaCadastrado(Integer id);
}
