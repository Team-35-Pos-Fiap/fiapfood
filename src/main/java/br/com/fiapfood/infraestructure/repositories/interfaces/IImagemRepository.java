package br.com.fiapfood.infraestructure.repositories.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.infraestructure.entities.ImagemEntity;

public interface IImagemRepository {
	void salvar(ImagemEntity imagem);
	ImagemCoreDto buscarPorId(UUID id);
}