package br.com.fiapfood.services.interfaces;

import java.util.List;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;

public interface IPerfilService {

    PerfilEntity buscarPorId(Integer id);
    List<PerfilRecordResponse> buscarTodos();
    boolean existePorId(Integer id);
}
