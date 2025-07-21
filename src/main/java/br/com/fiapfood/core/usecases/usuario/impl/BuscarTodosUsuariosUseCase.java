package br.com.fiapfood.core.usecases.usuario.impl;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioInputDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoInputDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoCoreDto;
import br.com.fiapfood.core.gateways.interfaces.IEnderecoGateway;
import br.com.fiapfood.core.gateways.interfaces.ILoginGateway;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.usuario.interfaces.IBuscarTodosUsuariosUseCase;

public class BuscarTodosUsuariosUseCase implements IBuscarTodosUsuariosUseCase{
	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;
	private final ILoginGateway loginGateway;
	private final IEnderecoGateway enderecoGateway;

	public BuscarTodosUsuariosUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
		this.loginGateway = loginGateway;
		this.enderecoGateway = enderecoGateway;
	}
	
	@Override
	public UsuarioPaginacaoCoreDto buscar(final Integer pagina) {
		final UsuarioPaginacaoInputDto dados = buscarTodos(pagina);
		
		return toUsuarioPaginacaoOutputDto(toListUsuarioDto(toListUsuario(dados.usuarios())), dados.paginacao());		
	}
	
	private UsuarioPaginacaoInputDto buscarTodos(final Integer pagina) {
		return usuarioGateway.buscarTodos(pagina);
	}
	
	private Perfil buscarPerfil(final Integer idPerfil) {
		return perfilGateway.buscarPorId(idPerfil);
	}
	
	private Login buscarLogin(final UUID idLogin) {
		return loginGateway.buscarPorId(idLogin);
	}
	
	private Endereco buscarEndereco(final UUID idEndereco) {
		return enderecoGateway.buscarPorId(idEndereco);
	}
	
	private List<Usuario> toListUsuario(final List<DadosUsuarioInputDto> usuarios) {
		return UsuarioPresenter.toListUsuario(usuarios);
	}
	
	private List<DadosUsuarioCoreDto> toListUsuarioDto(final List<Usuario> usuarios) {
		return usuarios.stream().map(u -> UsuarioPresenter.toUsuarioDto(u, 
																		buscarPerfil(u.getIdPerfil()), 
																		buscarLogin(u.getIdLogin()), 
																		buscarEndereco(u.getIdEndereco()))).toList();
	}
	
	private UsuarioPaginacaoCoreDto toUsuarioPaginacaoOutputDto(final List<DadosUsuarioCoreDto> usuarios, final PaginacaoCoreDto paginacao) {
		return UsuarioPresenter.toUsuarioPaginacaoOutputDto(usuarios, paginacao);
	}
}