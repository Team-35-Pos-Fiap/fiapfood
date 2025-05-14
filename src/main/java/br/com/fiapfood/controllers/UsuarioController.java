package br.com.fiapfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.controllers.docs.UsuarioDoc;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.SenhaRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.services.UsuarioService;
import br.com.fiapfood.utils.MensagensUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController implements UsuarioDoc {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	@PostMapping
	public ResponseEntity<Void> cadastrar(UsuarioRecordRequest usuario) {
		log.info("cadastrar():dados do usuário {}", usuario);
		
		usuarioService.cadastrar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(Integer id, UsuarioRecordRequest usuario) {
		log.info("atualizar():id {} - dados do usuário {}", id, usuario);
	
		usuarioService.atualizar(id, usuario);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<SucessoResponse> inativar(Integer id) {
		log.info("inativar():id {}", id);

		usuarioService.atualizarStatus(id, false);
		
		return ResponseEntity.ok(new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_INATIVACAO_USUARIO)));
	}
	
	@Override
	@PatchMapping("/{id}")
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
	@PatchMapping("/{id}/senha/altera")
	public ResponseEntity<SucessoResponse> trocarSenha(Integer id, SenhaRecordRequest dadosSenha) {
		log.info("trocar senha():id {} - senha {}", id, dadosSenha.senha());

		//usuarioService.trocarSenha(id, dadosSenha.senha());
		
		return ResponseEntity.ok(new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO)));
	}
}