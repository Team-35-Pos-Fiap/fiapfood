package br.com.fiapfood.integracao.entities;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import static br.com.fiapfood.utils.DataGenerator.validEnderecoEntity;
import static br.com.fiapfood.utils.DataGenerator.validPerfilEntity;
import static br.com.fiapfood.utils.DataGenerator.validLoginEntity;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsuarioEntityIT {

    @Autowired
    private TestEntityManager entityManager;

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() {
        // Arrange
        usuario = new UsuarioEntity();
        usuario.setNome("Usuario Teste");
        usuario.setEmail("usuario.test@email.com");
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setIsAtivo(true);
        usuario.setDadosEndereco(validEnderecoEntity());
        usuario.setPerfil(validPerfilEntity());
        usuario.setDadosLogin(validLoginEntity());
    }

    @Test
    void deveCriarNovoUsuario() {
        // Arrange
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("João Silva");
        usuario.setEmail("joao.silva@gmail.com");
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setIsAtivo(true);
        usuario.setDadosEndereco(validEnderecoEntity());
        usuario.setPerfil(validPerfilEntity());
        usuario.setDadosLogin(validLoginEntity());

        // Act
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Assert
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getId()).isNotNull();
        assertThat(usuarioSalvo.getNome()).isEqualTo("João Silva");
        assertThat(usuarioSalvo.getEmail()).isEqualTo("joao.silva@gmail.com");
        assertThat(usuarioSalvo.getIsAtivo()).isTrue();
    }

    @Test
    void deveGerarIdAutomaticamente() {
        // Assert
        assertThat(usuario.getId()).isNull();

        // Act
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Assert
        assertThat(usuarioSalvo.getId()).isNotNull().isInstanceOf(UUID.class);
    }

    @Test
    void deveCriarRelacaoComOutrasTabelas() {
        // Act
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);
        entityManager.clear();

        UsuarioEntity usuarioRecuperado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());

        // Assert
        assertThat(usuarioRecuperado.getPerfil())
                .isNotNull()
                .extracting(PerfilEntity::getNome)
                .isEqualTo("Cliente");

        assertThat(usuarioRecuperado.getDadosEndereco())
                .isNotNull()
                .extracting(EnderecoEntity::getCidade)
                .isEqualTo("São Gonçalo");

        assertThat(usuarioRecuperado.getDadosLogin())
                .isNotNull()
                .extracting(LoginEntity::getMatricula)
                .isEqualTo("us0010");
    }

    @Test
    void deveAtualizarNomeComSucesso() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.atualizarNome("Maria Santos");
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getNome()).isEqualTo("Maria Santos");
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveAtualizarEmailComSucesso() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.atualizarEmail("maria.santos@email.com");
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getEmail()).isEqualTo("maria.santos@email.com");
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveAtualizarPerfilComSucesso() {
        // Arrange
        PerfilEntity perfilDono = entityManager.find(PerfilEntity.class, 1);

        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.atualizarPerfil(perfilDono);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getPerfil().getNome()).isEqualTo("Dono");
        assertThat(usuarioAtualizado.getPerfil().getId()).isEqualTo(1);
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveAtualizarEnderecoComSucesso() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.atualizarEndereco("Nova Rua", "Rio de Janeiro", "Copacabana",
                "RJ", 456, "20000-000", "Cobertura", false);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getDadosEndereco().getEndereco()).isEqualTo("Nova Rua");
        assertThat(usuarioAtualizado.getDadosEndereco().getCidade()).isEqualTo("Rio de Janeiro");
        assertThat(usuarioAtualizado.getDadosEndereco().getBairro()).isEqualTo("Copacabana");
        assertThat(usuarioAtualizado.getDadosEndereco().getEstado()).isEqualTo("RJ");
        assertThat(usuarioAtualizado.getDadosEndereco().getNumero()).isEqualTo(456);
        assertThat(usuarioAtualizado.getDadosEndereco().getCep()).isEqualTo("20000-000");
        assertThat(usuarioAtualizado.getDadosEndereco().getComplemento()).isEqualTo("Cobertura");
        assertThat(usuarioAtualizado.getDadosEndereco().getSemNumero()).isEqualTo(false);
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveAtualizarLoginComSucesso() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.atualizarLogin("54321", "novaSenha123");
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getDadosLogin().getMatricula()).isEqualTo("54321");
        assertThat(usuarioAtualizado.getDadosLogin().getSenha()).isEqualTo("novaSenha123");
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveInativarUsuarioComSucesso() {
        // Arrange
        usuario.setIsAtivo(true);
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.inativar();
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getIsAtivo()).isFalse();
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveReativarUsuarioComSucesso() {
        // Arrange
        usuario.setIsAtivo(false);
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);

        // Act
        usuarioSalvo.reativar();
        entityManager.flush();
        entityManager.clear();

        // Assert
        UsuarioEntity usuarioAtualizado = entityManager.find(UsuarioEntity.class, usuarioSalvo.getId());
        assertThat(usuarioAtualizado.getIsAtivo()).isTrue();
        assertThat(usuarioAtualizado.getDataAtualizacao()).isNotNull();
    }

    @Test
    void deveRemoverEnderecoQuandoUsuarioRemovido() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);
        UUID enderecoId = usuarioSalvo.getDadosEndereco().getId();

        // Act
        entityManager.remove(usuarioSalvo);
        entityManager.flush();
        entityManager.clear();

        // Assert
        EnderecoEntity endereco = entityManager.find(EnderecoEntity.class, enderecoId);
        assertThat(endereco).isNull();
    }

    @Test
    void deveRemoverLoginQuandoUsuarioRemovido() {
        // Arrange
        UsuarioEntity usuarioSalvo = entityManager.persistAndFlush(usuario);
        UUID loginId = usuarioSalvo.getDadosLogin().getId();

        // Act
        entityManager.remove(usuarioSalvo);
        entityManager.flush();
        entityManager.clear();

        // Assert
        LoginEntity login = entityManager.find(LoginEntity.class, loginId);
        assertThat(login).isNull();
    }

}
