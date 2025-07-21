package br.com.fiapfood.core.exceptions.item;

public class RestauranteItemInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public RestauranteItemInvalidoException(String mensagem) {
        super(mensagem);
    }
}