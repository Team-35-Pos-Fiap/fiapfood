package br.com.fiapfood.core.controllers.interfaces;

import java.util.List;

import br.com.fiapfood.core.entities.dto.perfil.PerfilCoreDto;

public interface ITipoCulinariaController {
	List<PerfilCoreDto> buscarTodos();
	PerfilCoreDto buscarPorId(Integer id);
	void cadastrar(String nome);
	void atualizar(Integer id, String nome);
	void remover(Integer id);
}