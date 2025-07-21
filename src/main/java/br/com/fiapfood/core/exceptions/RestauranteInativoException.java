package br.com.fiapfood.core.exceptions;

public class RestauranteInativoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public RestauranteInativoException(String mensagem) {
        super(mensagem);
    }
}