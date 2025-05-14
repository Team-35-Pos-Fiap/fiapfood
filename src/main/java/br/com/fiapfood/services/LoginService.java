
package br.com.fiapfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.impl.UsuarioRepository;
import br.com.fiapfood.services.exceptions.UsuarioSemAcessoException;
import br.com.fiapfood.utils.MensagensUtil;

@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public String gerarTokenAcesso(LoginRecordRequest dados)  {
		try {
			usuarioRepository.recuperaDadosUsuarioPorLoginSenha(dados.matricula(), dados.senha());
			
			return "Acesso liberado";
		} catch(UsuarioNaoEncontradoException e) {
			throw new UsuarioSemAcessoException(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_USUARIO_SEM_PERMISSAO));
		}
	}
}