package br.com.fiapfood.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.controllers.response.MensagemResponse;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.request.SenhaRecordRequest;
import br.com.fiapfood.services.interfaces.ILoginService;
import br.com.fiapfood.utils.MensagensUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

	protected ILoginService loginService;

	public LoginController(ILoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	public ResponseEntity<MensagemResponse> validar(@RequestBody @Valid @NotNull LoginRecordRequest dados) {
		log.info("realizaLogin():dados do login {}", dados);
		MensagemResponse sucessoResponse = new SucessoResponse(loginService.validar(dados));

		return ResponseEntity.ok().body(sucessoResponse);
	}

	@PatchMapping("/{matricula}/senha")
	public ResponseEntity<MensagemResponse> atualizarSenha(@PathVariable @Valid @NotNull String matricula, @Valid @RequestBody @NotNull SenhaRecordRequest dados) {
		log.info("trocar senha():id {} - senha {}", matricula, dados.senha());

		loginService.trocarSenha(matricula, dados.senha());

		MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO));
		return ResponseEntity.ok(sucessoResponse);
	}
}