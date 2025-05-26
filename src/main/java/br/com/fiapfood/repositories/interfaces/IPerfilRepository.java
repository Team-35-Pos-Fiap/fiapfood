package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.PerfilEntity;

public interface IPerfilRepository {
    PerfilEntity buscarPorId(Integer id);
}
