package br.com.fiapfood.infraestructure.repositories.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.dto.item.ItemInputDto;

public interface IItemRepository {
    ItemInputDto buscarPorId(UUID id);
}