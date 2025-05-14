package br.com.fiapfood.controllers.exceptions;

import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import br.com.fiapfood.controllers.response.ErroResponse;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.services.exceptions.UsuarioSemAcessoException;
import br.com.fiapfood.utils.MensagensUtil;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataNullPointerException(NullPointerException e) { 
		log.error(e.getMessage(), e);
		
		return getInternalServerErrorResponse();
	}
		
	/*
	 * @ExceptionHandler(ConstraintViolationException.class)
	 * 
	 * @ResponseStatus(HttpStatus.BAD_REQUEST) public
	 * ResponseEntity<ErroListResponse>
	 * trataViolacaoRegrasNegocioException(ConstraintViolationException e) {
	 * log.error(e.getMessage(), e);
	 * 
	 * List<String> erros = new ArrayList<>();
	 * 
	 * e.getConstraintViolations().forEach(err -> erros.add(err.getMessage()));
	 * 
	 * return getBadRequestResponse(erros); }
	 */

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataValidacaoCamposException(ValidationException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	@ExceptionHandler(InternalServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErroResponse> trataInternalErrorException(InternalServerError e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	/*
	 * @ExceptionHandler(UsernameNotFoundException.class) public
	 * ResponseEntity<Erro> trataUsernameNotFoundException(UsernameNotFoundException
	 * e) { return getExpectationFailedResponse(e.getMessage()); }
	 */

	@ExceptionHandler(UsuarioSemAcessoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataUsuarioSemAcessoException(UsuarioSemAcessoException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataIllegalArgumentException(IllegalArgumentException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}

	@ExceptionHandler(NoSuchMessageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataNoSuchMessageException(NoSuchMessageException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
		
	protected ResponseEntity<ErroResponse> getInternalServerErrorResponse() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroResponse(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INTERNAL_SERVER_ERROR)));
	}
	
	protected ResponseEntity<ErroResponse> getBadRequestResponse(String mensagem) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(mensagem));
	}
	
	/*
	 * protected ResponseEntity<ErroListResponse> getBadRequestResponse(List<String>
	 * mensagens) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new
	 * ErroListResponse(mensagens)); }
	 */
	
	protected ResponseEntity<ErroResponse> getForbiddenResponse(String mensagem) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse(mensagem));
	}
}