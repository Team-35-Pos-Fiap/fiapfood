package br.com.fiapfood.core.usecases.item.impl;

import java.util.UUID;

import br.com.fiapfood.core.entities.Imagem;
import br.com.fiapfood.core.entities.Item;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarImagemItemUseCase;

public class AtualizarImagemItemUseCase implements IAtualizarImagemItemUseCase {

	private final IItemGateway itemGateway;
	private final IImagemGateway imagemGateway;

	public AtualizarImagemItemUseCase(IItemGateway itemGateway, IImagemGateway imagemGateway) {
		this.itemGateway = itemGateway;
		this.imagemGateway = imagemGateway;
	}

	@Override
	public void atualizar(UUID id, ImagemCoreDto dadosImagem) {
		final Item item = buscarItem(id);
		final Imagem imagem = buscarImagem(item.getIdImagem());
	
		atualizarDados(imagem, dadosImagem);
		salvar(imagem);
	}
	
	private void atualizarDados(Imagem imagem, ImagemCoreDto dadosImagem) {
		imagem.atualizarNome(dadosImagem.nome());
		imagem.atualizarTipo(dadosImagem.tipo());
		imagem.atualizarConteudo(dadosImagem.conteudo());
	}

	private void salvar(Imagem imagem) {
		imagemGateway.salvar(ImagemPresenter.toImagemDto(imagem));
	}

	private Imagem buscarImagem(UUID idImagem) {
		return imagemGateway.buscarPorId(idImagem);
	}

	private Item buscarItem(final UUID id) {
		return itemGateway.buscarPorId(id);
	}
}