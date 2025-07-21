package br.com.fiapfood.core.exceptions;

public class AtualizacaoEnderecoRestauranteNaoPermitidaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public AtualizacaoEnderecoRestauranteNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}