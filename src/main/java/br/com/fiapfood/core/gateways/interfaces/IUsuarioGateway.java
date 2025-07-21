package br.com.fiapfood.core.gateways.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoInputDto;

public interface IUsuarioGateway {
	Usuario buscarPorIdLogin(UUID idLogin);
	Usuario buscarPorId(UUID id);
	void salvar(DadosUsuarioCoreDto usuarioDto);
	boolean emailJaCadastrado(String email);
	List<Usuario> buscarPorIdPerfil(Integer idPerfil);
	UsuarioPaginacaoInputDto buscarTodos(Integer pagina);
}