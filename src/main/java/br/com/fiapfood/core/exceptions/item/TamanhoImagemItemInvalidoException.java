package br.com.fiapfood.core.exceptions.item;

public class TamanhoImagemItemInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public TamanhoImagemItemInvalidoException(String mensagem) {
        super(mensagem);
    }
}