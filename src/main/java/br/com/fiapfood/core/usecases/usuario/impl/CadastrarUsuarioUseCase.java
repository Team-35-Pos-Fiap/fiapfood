package br.com.fiapfood.core.usecases.usuario.impl;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.endereco.DadosEnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.login.LoginCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.CadastrarUsuarioCoreDto;
import br.com.fiapfood.core.exceptions.EmailDuplicadoException;
import br.com.fiapfood.core.exceptions.MatriculaDuplicadaException;
import br.com.fiapfood.core.gateways.interfaces.ILoginGateway;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.presenters.EnderecoPresenter;
import br.com.fiapfood.core.presenters.LoginPresenter;
import br.com.fiapfood.core.presenters.UsuarioPresenter;
import br.com.fiapfood.core.usecases.usuario.interfaces.ICadastrarUsuarioUseCase;

public class CadastrarUsuarioUseCase implements ICadastrarUsuarioUseCase {

	private final IUsuarioGateway usuarioGateway;
	private final IPerfilGateway perfilGateway;
	private final ILoginGateway loginGateway;
	
	public CadastrarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway) {
		this.usuarioGateway = usuarioGateway;
		this.perfilGateway = perfilGateway;
		this.loginGateway = loginGateway;
	}
	
	@Override
	public void cadastrar(final CadastrarUsuarioCoreDto usuarioDto) {
		validarEmail(usuarioDto.email());
		validarMatricula(usuarioDto.dadosLogin().matricula());

		final Usuario usuario = toUsuario(usuarioDto);
		
		salvar(usuario, usuarioDto);
	}
	
	private Perfil buscarPerfil(final Integer idPerfil) {
		return perfilGateway.buscarPorId(idPerfil);
	}
	
	private Usuario toUsuario(final CadastrarUsuarioCoreDto usuario) {
		return UsuarioPresenter.toUsuario(usuario);
	}
	
	private Login toLogin(final LoginCoreDto login) {
		return LoginPresenter.toLogin(login);
	}
	
	private Endereco toEndereco(final DadosEnderecoCoreDto endereco) {
		return EnderecoPresenter.toEndereco(endereco);
	}
	
	private void salvar(final Usuario usuario, final CadastrarUsuarioCoreDto usuarioDto) {
		usuarioGateway.salvar(UsuarioPresenter.toUsuarioDto(usuario, 
				   			  buscarPerfil(usuario.getIdPerfil()), 
				   			  toLogin(usuarioDto.dadosLogin()),
				   			  toEndereco(usuarioDto.dadosEndereco())));
	}
	
	private void validarEmail(final String email) {
		if(usuarioGateway.emailJaCadastrado(email)){
			throw new EmailDuplicadoException("Já existe um usuário com o email informado.");
		}
	}
	
	private void validarMatricula(final String matricula) {
		if(loginGateway.matriculaJaCadastrada(matricula)){
			throw new MatriculaDuplicadaException("Já existe um usuário com a matrícula informada.");
		}
	}
}