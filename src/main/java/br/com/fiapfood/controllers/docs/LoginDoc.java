package br.com.fiapfood.controllers.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.response.TokenRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Login", description = "Classe responsável por realizar as operações associadas ao login do usuário.")
public interface LoginDoc {

	@Operation(summary = "Realização a autenticação do usuário utilizando o seu usuário e a sua senha.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Token de acesso gerado com sucesso.", 
					content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TokenRecordResponse.class)) }),
		@ApiResponse(responseCode = "400", description = "Falha ao realizar a geração do token de acesso.", content = @Content)
	})	
	ResponseEntity<SucessoResponse> validar(@RequestBody @Valid LoginRecordRequest dados);

	
	@Operation(summary = "Realiza a alteração da senha do usuário.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204"),
		@ApiResponse(responseCode = "400", 
				     description = "Bad Request.", 
					 content = { @Content(mediaType = "application/json", 
					    				  schema = @Schema(implementation = ErroResponse.class), 
					    				  examples = @ExampleObject(value = "{\r\n"
						    				  	  + "    \"mensagem\": \"Ocorreu um erro ao realizar a troca da senha do usuário.\"\r\n"
						    				  	  + "}"))})
	})
	ResponseEntity<SucessoResponse> trocarSenha(@PathVariable @NotNull Integer id, @RequestBody @NotNull SenhaRecordRequest senha);
}