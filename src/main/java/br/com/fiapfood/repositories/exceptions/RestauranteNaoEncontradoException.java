package br.com.fiapfood.repositories.exceptions;

public class RestauranteNaoEncontradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
