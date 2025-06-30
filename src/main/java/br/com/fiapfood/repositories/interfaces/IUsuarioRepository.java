package br.com.fiapfood.repositories.interfaces;

import br.com.fiapfood.entities.db.UsuarioEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioRepository {

    UsuarioEntity recuperaDadosUsuarioPorId(UUID id);
    UsuarioEntity recuperaDadosUsuarioInativoPorId(UUID id);
    UsuarioEntity recuperaDadosUsuarioAtivoPorId(UUID id);
    UsuarioEntity recuperarDadosUsuarioPorIdLogin(UUID loginId);
    Page<UsuarioEntity> recuperaDadosUsuarios(final Integer pagina);
    void salvar(UsuarioEntity usuario);
    boolean emailJaCadastrado(String email);
}
