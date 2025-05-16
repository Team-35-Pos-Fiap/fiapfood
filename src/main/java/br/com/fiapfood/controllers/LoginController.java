package br.com.fiapfood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.controllers.docs.LoginDoc;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.services.LoginService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController implements LoginDoc {
	
	@Autowired
	protected LoginService loginService;
	
	@Override
	@PostMapping
	public ResponseEntity<SucessoResponse> validar(LoginRecordRequest dados) {		
		log.info("realizaLogin():dados do login {}", dados);

		return ResponseEntity.ok().body(new SucessoResponse(loginService.validar(dados)));		
	}
}