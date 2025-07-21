package br.com.fiapfood.core.exceptions;

public class DonoRestauranteInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DonoRestauranteInvalidoException(String mensagem) {
        super(mensagem);
    }
}