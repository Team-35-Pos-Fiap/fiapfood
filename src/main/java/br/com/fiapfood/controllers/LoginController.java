package br.com.fiapfood.controllers;

import br.com.fiapfood.controllers.response.MensagemResponse;
import br.com.fiapfood.services.interfaces.ILoginService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.services.LoginService;
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
}