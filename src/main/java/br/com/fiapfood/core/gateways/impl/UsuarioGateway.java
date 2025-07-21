package br.com.fiapfood.core.gateways.impl;

import java.util.List;
import java.util.UUID;

import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioInputDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoInputDto;
import br.com.fiapfood.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.EnderecoPresenter;
import br.com.fiapfood.core.presenters.LoginPresenter;
import br.com.fiapfood.core.presenters.PerfilPresenter;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.infraestructure.repositories.interfaces.IUsuarioRepository;

public class UsuarioGateway implements IUsuarioGateway {

	private final IUsuarioRepository usuarioRepository;
	
	public UsuarioGateway(IUsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public Usuario buscarPorIdLogin(final UUID idLogin) {
		final DadosUsuarioInputDto usuario = usuarioRepository.buscarPorIdLogin(idLogin);
		
		if(usuario != null) {
			return UsuarioPresenter.toUsuario(usuario);
		} else {
			throw new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o login informado.");
		}
	}
	
	@Override
	public Usuario buscarPorId(final UUID id) {
		final DadosUsuarioInputDto usuario = usuarioRepository.buscarPorId(id);
		
		if(usuario != null) {
			return UsuarioPresenter.toUsuario(usuario);
		} else {
			throw new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.");
		}
	}

	@Override
	public UsuarioPaginacaoInputDto buscarTodos(final Integer pagina) {
		final UsuarioPaginacaoInputDto dados = usuarioRepository.buscarTodos(pagina);
			
		if(dados != null) {
			return dados;
		} else {
			throw new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados para a página informada.");
		}
	}

	@Override
	public void salvar(final DadosUsuarioCoreDto usuario) {
		usuarioRepository.salvar(UsuarioPresenter.toUsuarioEntity(usuario, 
											 				      EnderecoPresenter.toEnderecoEntity(usuario.endereco()), 
															      PerfilPresenter.toPerfilEntity(usuario.perfil()), 
															      LoginPresenter.toLoginEntity(usuario.login())));		
	}

	@Override
	public boolean emailJaCadastrado(final String email) {
		return usuarioRepository.emailJaCadastrado(email);
	}

	@Override
	public List<Usuario> buscarPorIdPerfil(Integer idPerfil) {
		List<DadosUsuarioInputDto> usuarios = usuarioRepository.buscarPorIdPerfil(idPerfil);
		
		if(usuarios != null) {
			return UsuarioPresenter.toListUsuario(usuarios);
		} else {
			return null;
		}
	}
}