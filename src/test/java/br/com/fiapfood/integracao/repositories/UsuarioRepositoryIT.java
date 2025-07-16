package br.com.fiapfood.integracao.repositories;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.impl.UsuarioRepository;
import br.com.fiapfood.services.exceptions.PaginaInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validUsuarioEntity;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsuarioRepositoryIT {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity usuarioEntity;

    @BeforeEach
    void setUp() {
        usuarioEntity = validUsuarioEntity();
    }

    @Test
    void deveSalvarNovoUsuarioComSucesso() {
        // Act
        usuarioRepository.salvar(usuarioEntity);

        // Assert
        UsuarioEntity usuarioSalvo = usuarioRepository.recuperaDadosUsuarioPorId(usuarioEntity.getId());
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isEqualTo(usuarioEntity.getId());
        assertThat(usuarioSalvo.getNome()).isEqualTo(usuarioEntity.getNome());
        assertThat(usuarioSalvo.getEmail()).isEqualTo(usuarioEntity.getEmail());
        assertThat(usuarioSalvo.getIsAtivo()).isEqualTo(usuarioEntity.getIsAtivo());
    }

    @Test
    void deveBuscarUsuarioPorId() {
        // Arrange
        UUID usuarioIdExistente = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

        // Act
        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperaDadosUsuarioPorId(usuarioIdExistente);

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(usuarioIdExistente);
        assertThat(usuarioEncontrado.getNome()).isNotBlank();
        assertThat(usuarioEncontrado.getEmail()).isNotBlank();
    }

    @Test
    void deveBuscarUsuarioAtivoPorId() {
        // Arrange
        UUID usuarioIdExistente = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");

        // Act
        UsuarioEntity usuarioAtivo = usuarioRepository.recuperaDadosUsuarioAtivoPorId(usuarioIdExistente);

        // Assert
        assertThat(usuarioAtivo).isNotNull();
        assertThat(usuarioAtivo.getId()).isEqualTo(usuarioIdExistente);
        assertThat(usuarioAtivo.getIsAtivo()).isTrue();
    }

    @Test
    void deveBuscarUsuarioInativoPorId() {
        // Act
        UUID usuarioIdInativo = UUID.fromString("60127300-b56a-4394-a208-d9ef8eb864c7");
        UsuarioEntity usuarioInativo = usuarioRepository.recuperaDadosUsuarioInativoPorId(usuarioIdInativo);

        // Assert
        assertThat(usuarioInativo).isNotNull();
        assertThat(usuarioInativo.getId()).isEqualTo(usuarioIdInativo);
        assertThat(usuarioInativo.getIsAtivo()).isFalse();
    }

//    @Test
//    void deveBuscarUsuarioPorIdLogin() {
//        // Arrange
//        UUID loginIdExistente = UUID.fromString("c303266f-9d32-4dde-8f4c-d8ee13b24ae9");
//
//        // Act
//        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginIdExistente);
//
//        // Assert
//        assertThat(usuarioEncontrado).isNotNull();
//        assertThat(usuarioEncontrado.getDadosLogin().getId()).isEqualTo(loginIdExistente);
//    }

    @Test
    void deveVerificarEmailJaCadastrado() {
        // Arrange
        String emailCadastrado = "thiago@fiapfood.com";

        // Act
        boolean emailExiste = usuarioRepository.emailJaCadastrado(emailCadastrado);
        boolean emailNaoExiste = usuarioRepository.emailJaCadastrado("email.inexistente@example.com");

        // Assert
        assertThat(emailExiste).isTrue();
        assertThat(emailNaoExiste).isFalse();
    }

    @Test
    void deveBuscarUsuariosPaginadosComSucesso() {
        // Act
        Page<UsuarioEntity> paginaUsuarios = usuarioRepository.recuperaDadosUsuarios(1);

        // Assert
        assertThat(paginaUsuarios).isNotNull();
        assertThat(paginaUsuarios.getContent()).isNotEmpty();
        assertThat(paginaUsuarios.getContent().size()).isLessThanOrEqualTo(4);
        assertThat(paginaUsuarios.getNumber()).isEqualTo(0);
    }

    @Test
    void deveLancarExceptionSePaginaInvalida() {
        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(0))
                .isInstanceOf(PaginaInvalidaException.class);

        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(-1))
                .isInstanceOf(PaginaInvalidaException.class);

        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(null))
                .isInstanceOf(PaginaInvalidaException.class);
    }

    @Test
    void deveLancarExceptionSeNenhumUsuarioEncontradoNaPagina() {
        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(999))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("Não foram encontrados usuários na base de dados.");
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        // Arrange
        UUID usuarioIdExistente = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");
        UsuarioEntity usuarioExistente = usuarioRepository.recuperaDadosUsuarioPorId(usuarioIdExistente);
        String nomeOriginal = usuarioExistente.getNome();
        String novoNome = "Nome Atualizado";

        // Act
        usuarioExistente.setNome(novoNome);
        usuarioRepository.salvar(usuarioExistente);

        // Assert
        UsuarioEntity usuarioAtualizado = usuarioRepository.recuperaDadosUsuarioPorId(usuarioIdExistente);
        assertThat(usuarioAtualizado).isNotNull();
        assertThat(usuarioAtualizado.getId()).isEqualTo(usuarioIdExistente);
        assertThat(usuarioAtualizado.getNome()).isEqualTo(novoNome);
        assertThat(usuarioAtualizado.getNome()).isNotEqualTo(nomeOriginal);
    }

    @Test
    void deveLancarExceptionAoBuscarUsuarioInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioPorId(idInexistente))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("Usuário não encontrado na base de dados.");
    }

    @Test
    void deveLancarExceptionAoBuscarUsuarioAtivoPorIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioAtivoPorId(idInexistente))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("Usuário não encontrado na base de dados.");
    }

    @Test
    void deveLancarExceptionAoBuscarUsuarioInativoPorIdInexistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioInativoPorId(idInexistente))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessageContaining("Usuário não encontrado na base de dados.");
    }

    @Test
    void deveVerificarSeUsuarioAtivoNaoERetornadoNaBuscaPorInativo() {
        // Assert
        UUID usuarioIdAtivo = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioInativoPorId(usuarioIdAtivo))
                .isInstanceOf(UsuarioNaoEncontradoException.class);
    }

    @Test
    void deveVerificarSeUsuarioInativoNaoERetornadoNaBuscaPorAtivo() {
        // Assert
        UUID usuarioIdInativo = UUID.fromString("60127300-b56a-4394-a208-d9ef8eb864c7");

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioAtivoPorId(usuarioIdInativo))
                .isInstanceOf(UsuarioNaoEncontradoException.class);
    }

}
