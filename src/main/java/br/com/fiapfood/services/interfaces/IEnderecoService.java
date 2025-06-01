package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

public interface IEnderecoService {
    void atualizarEndereco(EnderecoEntity enderecoAtual, EnderecoRecordRequest dados);
}
