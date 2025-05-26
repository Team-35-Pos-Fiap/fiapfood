package br.com.fiapfood.controllers;

import br.com.fiapfood.controllers.response.MensagemResponse;
import br.com.fiapfood.services.interfaces.IUsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

	private final IUsuarioService usuarioService;

	public UsuarioController(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull UsuarioRecordRequest usuario) {
		log.info("cadastrar():dados do usuário {}", usuario);
		
		usuarioService.cadastrar(usuario);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}/status")
	public ResponseEntity<MensagemResponse> inativar(@Valid @PathVariable @NotNull UUID id) {
		log.info("inativar():id {}", id);

		usuarioService.atualizarStatus(id, false);

		MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_INATIVACAO_USUARIO));
		return ResponseEntity.ok(sucessoResponse);
	}
	
	@PatchMapping("/{id}/status")
	public ResponseEntity<MensagemResponse> reativar(@Valid @PathVariable @NotNull UUID id) {
		log.info("reativar():id {}", id);

		usuarioService.atualizarStatus(id, true);

		MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_REATIVACAO_USUARIO));
		return ResponseEntity.ok(sucessoResponse);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioRecordResponse> buscarUsuarioPorId(@PathVariable @NotNull @Valid UUID id) {
		log.info("buscarUsuarioPorId():id {}", id);
		
		return ResponseEntity.ok().body(usuarioService.buscarPorId(id));
	}
	
	@GetMapping
	public ResponseEntity<UsuarioRecordPaginacaoResponse> buscarUsuarios(@RequestParam(defaultValue = "1") final Integer pagina) {
		log.info("buscarUsuarios() - pagina {}", pagina);

		return ResponseEntity.ok().body(usuarioService.buscarUsuarios(pagina));
	}

	@PatchMapping("/{id}/perfil")
	public ResponseEntity<Void> atualizarPerfil(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull PerfilRecordRequest dadosPerfil) {
		usuarioService.atualizarPerfil(id, dadosPerfil.idPerfil());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@PatchMapping("/{id}/endereco")
	public ResponseEntity<Void> atualizarEndereco(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull EnderecoRecordRequest dadosEndereco) {
		usuarioService.atualizarDadosEndereco(id, dadosEndereco);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@PatchMapping("/{id}/nome")
	public ResponseEntity<Void> atualizarNome(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull NomeRecordRequest dados) {
		usuarioService.atualizarNome(id, dados.nome());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

	@PatchMapping("/{id}/email")
	public ResponseEntity<Void> atualizarEmail(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull EmailRecordRequest dados) {
		usuarioService.atualizarEmail(id, dados.email());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}

}