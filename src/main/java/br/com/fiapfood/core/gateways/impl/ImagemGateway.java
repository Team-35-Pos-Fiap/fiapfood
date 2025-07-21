package br.com.fiapfood.core.gateways.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.IImagemRepository;

public class ImagemGateway implements IImagemGateway {

	private final IImagemRepository imagemRepository;
	
	public ImagemGateway(IImagemRepository imagemRepository) {
		this.imagemRepository = imagemRepository;
	}
	
	@Override
	public void salvar(ImagemCoreDto imagem) {
		imagemRepository.salvar(ImagemPresenter.toImagemEntity(imagem));
	}

	@Override
	public Imagem buscarPorId(UUID id) {
		ImagemCoreDto imagem = imagemRepository.buscarPorId(id);
		
		if(imagem != null) { 
			return ImagemPresenter.toImagem(imagem);
		} else {
			return null;
		}
	}
}