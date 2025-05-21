package br.com.fiapfood.controllers.exceptions;

import br.com.fiapfood.services.exceptions.PaginaInvalidaException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import br.com.fiapfood.controllers.response.ErroResponse;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.services.exceptions.LoginSemAcessoException;
import br.com.fiapfood.utils.MensagensUtil;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataNullPointerException(NullPointerException e) { 
		log.error(e.getMessage(), e);
		
		return getInternalServerErrorResponse();
	}

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

	@ExceptionHandler(LoginSemAcessoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataLoginSemAcessoException(LoginSemAcessoException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	@ExceptionHandler(LoginNaoEncontradoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataLoginNaoEncontradoException(LoginNaoEncontradoException e) {
		log.error(e.getMessage(), e);
		
		return getBadRequestResponse(e.getMessage());
	}
	
	protected ResponseEntity<ErroResponse> getInternalServerErrorResponse() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroResponse(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_INTERNAL_SERVER_ERROR)));
	}
	
	protected ResponseEntity<ErroResponse> getBadRequestResponse(String mensagem) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(mensagem));
	}
	
	protected ResponseEntity<ErroResponse> getForbiddenResponse(String mensagem) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse(mensagem));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> trataMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		log.error(e.getMessage(), e);
		return getBadRequestResponse(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PARAMETRO_INVALIDO, e.getName(), e.getRequiredType().getSimpleName()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getAllErrors().forEach(error -> {
			String field = error instanceof FieldError fe ? fe.getField() : "objeto";
			errors.put(field, error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(PaginaInvalidaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErroResponse> handlePaginaInvalidaException(PaginaInvalidaException e) {
		return ResponseEntity.badRequest()
				.body(new ErroResponse(e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
		Map<String, String> error = new HashMap<>();
		error.put("erro", ex.getClass().getName());
		error.put("mensagem", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}