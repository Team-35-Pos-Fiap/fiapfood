package br.com.fiapfood.core.controllers.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.fiapfood.core.controllers.interfaces.IItemCoreController;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.presenters.ImagemPresenter;
import br.com.fiapfood.core.presenters.ItemPresenter;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDescricaoItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDisponibilidadeConsumoPresencialItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDisponibilidadeItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarNomeItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarPrecoItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBaixarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarItemPorIdUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarTodosItensUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.ICadastrarItemUseCase;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemPaginacaoDto;

public class ItemCoreController implements IItemCoreController {

	private final IBuscarItemPorIdUseCase buscarItemPorIdUseCase;
	private final IBuscarTodosItensUseCase buscarTodosItensUseCase;
	private final ICadastrarItemUseCase cadastrarItemUseCase;
	private final IAtualizarDescricaoItemUseCase atualizarDescricaoItemUseCase;
	private final IAtualizarDisponibilidadeConsumoPresencialItemUseCase atualizarDisponibilidadeConsumoPresencialItemUseCase;
	private final IAtualizarDisponibilidadeItemUseCase atualizarDisponibilidadeItemUseCase;
	private final IAtualizarImagemItemUseCase atualizarImagemItemUseCase;
	private final IAtualizarNomeItemUseCase atualizarNomeItemUseCase;
	private final IAtualizarPrecoItemUseCase atualizarPrecoItemUseCase;
	private final IBaixarImagemItemUseCase baixarImagemItemUseCase;

	public ItemCoreController(IBuscarItemPorIdUseCase buscarItemPorIdUseCase, IBuscarTodosItensUseCase buscarTodosItensUseCase, ICadastrarItemUseCase cadastrarItemUseCase,
							  IAtualizarDescricaoItemUseCase atualizarDescricaoItemUseCase, IAtualizarDisponibilidadeConsumoPresencialItemUseCase atualizarDisponibilidadeConsumoPresencialItemUseCase,
							  IAtualizarDisponibilidadeItemUseCase atualizarDisponibilidadeItemUseCase, IAtualizarImagemItemUseCase atualizarImagemItemUseCase, 
							  IAtualizarNomeItemUseCase atualizarNomeItemUseCase, IAtualizarPrecoItemUseCase atualizarPrecoItemUseCase,
							  IBaixarImagemItemUseCase baixarImagemItemUseCase) {
		this.buscarItemPorIdUseCase = buscarItemPorIdUseCase;
		this.buscarTodosItensUseCase = buscarTodosItensUseCase;
		this.cadastrarItemUseCase = cadastrarItemUseCase;
		this.atualizarDescricaoItemUseCase = atualizarDescricaoItemUseCase;
		this.atualizarDisponibilidadeConsumoPresencialItemUseCase = atualizarDisponibilidadeConsumoPresencialItemUseCase;
		this.atualizarDisponibilidadeItemUseCase = atualizarDisponibilidadeItemUseCase;
		this.atualizarImagemItemUseCase = atualizarImagemItemUseCase;
		this.atualizarNomeItemUseCase = atualizarNomeItemUseCase;
		this.atualizarPrecoItemUseCase = atualizarPrecoItemUseCase;
		this.baixarImagemItemUseCase = baixarImagemItemUseCase;
	}
	
	@Override
	public ItemDto buscarPorId(final UUID id) {
		return ItemPresenter.toItemOutputDto(buscarItemPorIdUseCase.buscar(id));
	}

	@Override
	public ItemPaginacaoDto buscarTodos(final Integer pagina) {
		return ItemPresenter.ItemPaginacaoOutputCoreDto(buscarTodosItensUseCase.buscar(pagina));
	}
	
	@Override
	public void atualizarDescricao(final UUID id, final String descricao) {
		atualizarDescricaoItemUseCase.atualizar(id, descricao);
	}
	
	@Override
	public void atualizarNome(final UUID id, final String nome) {
		atualizarNomeItemUseCase.atualizar(id, nome);
	}
	
	@Override
	public void atualizarPreco(final UUID id, final BigDecimal preco) {
		atualizarPrecoItemUseCase.atualizar(id, preco);
	}

	@Override
	public void atualizarDisponibilidadeConsumoPresencial(final UUID id, final Boolean isDisponivelParaConsumoPresencial) {
		atualizarDisponibilidadeConsumoPresencialItemUseCase.atualizar(id, isDisponivelParaConsumoPresencial);
	}
	
	@Override
	public void atualizarDisponibilidade(final UUID id, final Boolean isDisponivel) {
		atualizarDisponibilidadeItemUseCase.atualizar(id, isDisponivel);
	}
	
	@Override
	public void atualizarImagem(final UUID id, final MultipartFile imagem) {
		try {
			atualizarImagemItemUseCase.atualizar(id, ImagemPresenter.toImagemDto(imagem));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cadastrar(final String nome, final String descricao, final BigDecimal preco, 
						  final Boolean disponivelParaConsumoPresencial, final MultipartFile imagem, UUID idRestaurante) {
		try {
			cadastrarItemUseCase.cadastrar(nome, descricao, preco, disponivelParaConsumoPresencial, ImagemPresenter.toImagemDto(imagem), idRestaurante);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ImagemCoreDto baixarImagem(final UUID id) {
		return baixarImagemItemUseCase.baixar(id);
	}
}