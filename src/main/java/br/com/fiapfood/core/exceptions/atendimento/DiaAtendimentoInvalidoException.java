package br.com.fiapfood.core.exceptions.atendimento;

public class DiaAtendimentoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DiaAtendimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}