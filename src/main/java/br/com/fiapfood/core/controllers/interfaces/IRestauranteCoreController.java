package br.com.fiapfood.core.controllers.interfaces;

import java.util.UUID;

import br.com.fiapfood.infraestructure.controllers.request.atendimento.AtendimentoDto;
import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.CadastrarRestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestaurantePaginacaoDto;

public interface IRestauranteCoreController {
	RestaurantePaginacaoDto buscarTodos(Integer pagina);
	RestauranteDto buscarPorId(UUID id);
	void cadastrar(CadastrarRestauranteDto restaurante);
	void reativar(UUID idRestaurante);
	void inativar(UUID idRestaurante);
	void atualizarTipoCulinaria(UUID idRestaurante, Integer tipoCulinaria);
	void atualizarNome(UUID idRestaurante, String nome);
	void atualizarDono(UUID idRestaurante, UUID idDono);
	void atualizarEndereco(UUID idRestaurante, DadosEnderecoDto endereco);
	void atualizarAtendimento(UUID idRestaurante, AtendimentoDto atendimento);
	void excluirAtendimento(UUID idRestaurante, UUID idAtendimento);
	void adicionarAtendimento(UUID idRestaurante, AtendimentoDto atendimento);
}