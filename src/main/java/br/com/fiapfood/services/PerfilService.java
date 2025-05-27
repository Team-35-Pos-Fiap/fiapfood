package br.com.fiapfood.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.mappers.PerfilMapper;
import br.com.fiapfood.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.services.interfaces.IPerfilService;

@Service
public class PerfilService implements IPerfilService {

    private final IPerfilRepository perfilRepository;

    public PerfilService(IPerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @Override
    public PerfilEntity buscarPorId(Integer id) {
        return perfilRepository.buscarPorId(id);
    }

    @Override
    public List<PerfilRecordResponse> buscarTodos() {
        return PerfilMapper.toPerfilRecord(perfilRepository.buscarTodos());
    }
}