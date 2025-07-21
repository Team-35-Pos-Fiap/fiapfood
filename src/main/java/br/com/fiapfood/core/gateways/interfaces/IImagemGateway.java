package br.com.fiapfood.core.gateways.interfaces;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;

public interface IImagemGateway {
	void salvar(ImagemCoreDto imagem);
	Imagem buscarPorId(UUID id);
}