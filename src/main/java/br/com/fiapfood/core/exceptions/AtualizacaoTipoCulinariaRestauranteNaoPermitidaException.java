package br.com.fiapfood.core.exceptions;

public class AtualizacaoTipoCulinariaRestauranteNaoPermitidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public AtualizacaoTipoCulinariaRestauranteNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}