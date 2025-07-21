package br.com.fiapfood.infraestructure.repositories.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.infraestructure.entities.ImagemEntity;
import br.com.fiapfood.infraestructure.repositories.interfaces.IImagemRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.jpa.IImagemJpaRepository;

@Repository
public class ImagemRepository implements IImagemRepository {

	private final IImagemJpaRepository imagemRepository; 
	
	public ImagemRepository(IImagemJpaRepository imagemRepository) {
		this.imagemRepository = imagemRepository;
	}
	
	@Override
	public void salvar(ImagemEntity imagem) {
		imagemRepository.save(imagem);
	}

	@Override
	public ImagemCoreDto buscarPorId(UUID id) {
		Optional<ImagemEntity> dados = imagemRepository.findById(id);
		
		if(dados.isPresent()) {
			return ImagemPresenter.toImagemDto(dados.get());
		} else {
			return null;			
		}
	}
}
