package br.com.fiapfood.core.gateways.interfaces;

import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.UsuarioDto;

import java.util.Map;
import java.util.UUID;

public interface IRestauranteGateway {
	Usuario buscarPorIdLogin(UUID idLogin);
	Usuario buscarPorId(UUID id);
	Map<Class<?>, Object> buscarUsuariosComPaginacao(Integer pagina);
	void salvar(UsuarioDto usuarioDto);
	boolean emailJaCadastrado(String email);
}