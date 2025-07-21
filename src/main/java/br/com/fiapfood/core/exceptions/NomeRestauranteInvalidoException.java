package br.com.fiapfood.core.exceptions;

public class NomeRestauranteInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public NomeRestauranteInvalidoException(String mensagem) {
        super(mensagem);
    }
}