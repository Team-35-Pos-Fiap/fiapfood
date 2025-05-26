
package br.com.fiapfood.services;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.impl.UsuarioRepository;
import br.com.fiapfood.services.interfaces.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.impl.LoginRepository;
import br.com.fiapfood.services.exceptions.LoginSemAcessoException;
import br.com.fiapfood.utils.MensagensUtil;

import java.util.UUID;

@Service
public class LoginService implements ILoginService {

	private final LoginRepository loginRepository;

	private final UsuarioService usuarioService;

	public LoginService(LoginRepository loginRepository, UsuarioService usuarioService) {
		this.loginRepository = loginRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	public String validar(LoginRecordRequest dados) {
		try {
			LoginEntity login = loginRepository.buscarPorMatriculaSenha(dados.matricula(), dados.senha());
			usuarioService.buscarUsuarioPorIdLogin(login.getId()); // Serve para validar se usuario esta ativo

			return "Acesso liberado";
		} catch (UsuarioNaoEncontradoException e) {
			throw new LoginSemAcessoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_SEM_PERMISSAO));
		}
	}

	@Override
	public void trocarSenha(String matricula, String senha) {
		LoginEntity login = loginRepository.buscarPorMatricula(matricula);
		usuarioService.buscarUsuarioPorIdLogin(login.getId()); // Serve para validar se usuario esta ativo

		login.atualizarSenha(senha);

		loginRepository.salvar(login);
	}

//	@Override
//	public void atualizarMatricula(UUID idUsuario, String matricula) {
//		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(idUsuario);
//		LoginEntity login = loginRepository.buscarPorId(usuario.getDadosLogin().getId());
//
//		login.atualizarMatricula(matricula);
//
//		loginRepository.salvar(login);
//	}
}