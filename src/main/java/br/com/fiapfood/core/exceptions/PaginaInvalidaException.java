package br.com.fiapfood.core.exceptions;

import br.com.fiapfood.infraestructure.utils.MensagensUtil;

public class PaginaInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public PaginaInvalidaException() {
        super(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PAGINA_INVALIDA));
    }
}