package br.com.fiapfood.core.controllers.interfaces;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemPaginacaoDto;

public interface IItemCoreController {
	ItemDto buscarPorId(UUID id);
	ItemPaginacaoDto buscarTodos(Integer pagina);
	void cadastrar(String nome, String descricao,  BigDecimal preco, 
				   Boolean disponivelParaConsumoPresencial, MultipartFile imagem, UUID idRestaurante);
	void atualizarImagem(UUID id, MultipartFile imagem);
	void atualizarDisponibilidade(UUID id, Boolean isDisponivel);
	void atualizarDisponibilidadeConsumoPresencial(UUID id, Boolean isDisponivelParaConsumoPresencial);
	void atualizarNome(UUID id, String nome);
	void atualizarDescricao(UUID id, String descricao);
	void atualizarPreco(UUID id, BigDecimal preco);
	ImagemCoreDto baixarImagem(UUID id);
}