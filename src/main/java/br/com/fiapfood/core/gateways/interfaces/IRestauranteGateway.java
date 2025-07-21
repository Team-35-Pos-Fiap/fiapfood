package br.com.fiapfood.core.gateways.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Restaurante;
import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.restaurante.DadosRestauranteDto;
import br.com.fiapfood.core.entities.dto.restaurante.RestaurantePaginacaoInputDto;

public interface IRestauranteGateway {
	Restaurante buscarPorId(UUID id);
	RestaurantePaginacaoInputDto buscarTodos(Integer pagina);
	void atualizar(DadosRestauranteDto restaurante);
	void atualizarAtendimentos(DadosRestauranteDto restaurante);	
	void cadastrar(DadosRestauranteDto restaurante, EnderecoCoreDto endereco, List<AtendimentoCoreDto> atendimento);
}