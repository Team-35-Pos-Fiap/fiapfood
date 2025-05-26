package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;

import java.util.List;
import java.util.UUID;

public interface IPerfilService {

    PerfilEntity buscarPorId(Integer id);
    List<PerfilRecordResponse> buscarTodos();
}
