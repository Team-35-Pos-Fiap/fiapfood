package br.com.fiapfood.services.exceptions;

import br.com.fiapfood.utils.MensagensUtil;

public class PaginaInvalidaException extends RuntimeException {
    public PaginaInvalidaException() {
        super(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PAGINA_INVALIDA));
    }
}