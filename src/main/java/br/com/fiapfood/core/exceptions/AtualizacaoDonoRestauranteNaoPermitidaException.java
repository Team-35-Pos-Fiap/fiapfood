package br.com.fiapfood.core.exceptions;

public class AtualizacaoDonoRestauranteNaoPermitidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public AtualizacaoDonoRestauranteNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}