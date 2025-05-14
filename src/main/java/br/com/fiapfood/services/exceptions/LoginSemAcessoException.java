package br.com.fiapfood.services.exceptions;

public class LoginSemAcessoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public LoginSemAcessoException(String mensagem) {
		super(mensagem);
	}
}
