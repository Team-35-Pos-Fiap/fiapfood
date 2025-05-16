package br.com.fiapfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.controllers.docs.UsuarioDoc;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.EmailRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.request.NomeRecordRequest;
import br.com.fiapfood.entities.record.request.PerfilRecordRequest;
import br.com.fiapfood.entities.record.request.SenhaRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.services.EnderecoService;
import br.com.fiapfood.services.LoginService;
import br.com.fiapfood.services.UsuarioService;
import br.com.fiapfood.utils.MensagensUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController implements UsuarioDoc {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private EnderecoService enderecoService;
	
	@Override
	@PostMapping
	public ResponseEntity<Void> cadastrar(UsuarioRecordRequest usuario) {
		log.info("cadastrar():dados do usuário {}", usuario);
		
		usuarioService.cadastrar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	@DeleteMapping("/{id}/status")
	public ResponseEntity<SucessoResponse> inativar(Integer id) {
		log.info("inativar():id {}", id);

		usuarioService.atualizarStatus(id, false);
		
		return ResponseEntity.ok(new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_INATIVACAO_USUARIO)));
	}
	
	@Override
	@PatchMapping("/{id}/status")
	public ResponseEntity<SucessoResponse> reativar(Integer id) {
		log.info("reativar():id {}", id);

		usuarioService.atualizarStatus(id, true);
		
		return ResponseEntity.ok(new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_REATIVACAO_USUARIO)));
	}
	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioRecordResponse> buscarUsuarioPorId(Integer id) {
		log.info("buscarUsuarioPorId():id {}", id);
		
		return ResponseEntity.ok().body(usuarioService.buscarPorId(id));
	}
	
	@Override
	@GetMapping
	public ResponseEntity<UsuarioRecordPaginacaoResponse> buscarUsuarios(final Integer pagina) {
		log.info("buscarUsuarios() - pagina {}", pagina);

		return ResponseEntity.ok().body(usuarioService.buscarUsuarios(pagina));
	}

	@Override
	@PatchMapping("/{id}/perfil")
	public ResponseEntity<Void> atualizarPerfil(Integer id, PerfilRecordRequest dadosPerfil) {
		usuarioService.atualizarPerfil(id, dadosPerfil.idPerfil());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@Override
	@PatchMapping("/{id}/login")
	public ResponseEntity<Void> atualizarLogin(Integer id, LoginRecordRequest dadosLogin) {
		log.info("atualizarLogin() - id {} dados login: {}", id, dadosLogin);

		loginService.atualizarMatricula(id, dadosLogin.matricula());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@Override
	@PatchMapping("/{id}/endereco")
	public ResponseEntity<Void> atualizarEndereco(Integer id, EnderecoRecordRequest dadosEndereco) {
		enderecoService.atualizarEndereco(id, dadosEndereco);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@Override
	@PatchMapping("/{id}/nome")	
	public ResponseEntity<Void> atualizarNome(Integer id, NomeRecordRequest dados) {
		usuarioService.atualizarNome(id, dados.nome());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@Override
	@PatchMapping("/{id}/email")	
	public ResponseEntity<Void> atualizarEmail(Integer id, EmailRecordRequest dados) {
		usuarioService.atualizarEmail(id, dados.email());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}
	
	@Override
	@PatchMapping("/{id}/senha")
	public ResponseEntity<SucessoResponse> atualizarSenha(Integer id, SenhaRecordRequest dados) {
		log.info("trocar senha():id {} - senha {}", id, dados.senha());

		loginService.trocarSenha(id, dados.senha());
		
		return ResponseEntity.ok(new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO)));
	}
}