package br.com.fiapfood.core.exceptions;

public class RestauranteDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public RestauranteDuplicadoException(String mensagem) {
        super(mensagem);
    }
}