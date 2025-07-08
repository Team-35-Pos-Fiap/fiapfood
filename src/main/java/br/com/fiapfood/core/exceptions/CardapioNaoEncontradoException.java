package br.com.fiapfood.core.exceptions;

public class CardapioNaoEncontradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CardapioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
