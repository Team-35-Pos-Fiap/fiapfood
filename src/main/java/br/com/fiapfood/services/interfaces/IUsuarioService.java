package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioService {
    UsuarioRecordResponse buscarPorId(UUID id);
    UsuarioRecordPaginacaoResponse buscarUsuarios(final Integer pagina);
    void cadastrar(UsuarioRecordRequest usuario);
    void atualizarStatus(UUID id, boolean isAtivo);
    void salvar(UsuarioEntity usuario);
    void atualizarPerfil(UUID id, Integer idPerfil);
    void atualizarNome(UUID id, String nome);
    void atualizarEmail(UUID id, String email);
    UsuarioEntity buscarUsuarioPorIdLogin(UUID loginId);
    void atualizarDadosEndereco(UUID id, EnderecoRecordRequest dadosEndereco);
}
