package br.com.fiapfood.core.exceptions;

public class NomeTipoCulinariaInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public NomeTipoCulinariaInvalidoException(String mensagem) {
        super(mensagem);
    }
}