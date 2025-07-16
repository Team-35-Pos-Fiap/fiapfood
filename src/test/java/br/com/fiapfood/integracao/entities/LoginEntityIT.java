package br.com.fiapfood.integracao.entities;


import br.com.fiapfood.entities.db.LoginEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LoginEntityIT {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveCadastrarLoginComSucesso() {
        // Arrange
        LoginEntity login = new LoginEntity();
        login.setMatricula("123456");
        login.setSenha("novaSenha");

        // Act
        LoginEntity loginSalvo = entityManager.persistAndFlush(login);

        // Assert
        assertThat(loginSalvo).isNotNull();
        assertThat(loginSalvo.getId()).isNotNull();
        assertThat(loginSalvo.getMatricula()).isEqualTo("123456");
        assertThat(loginSalvo.getSenha()).isEqualTo("novaSenha");
    }

    @Test
    void deveGerarIdAutomaticamente() {
        // Arrange
        LoginEntity login = new LoginEntity();
        login.setMatricula("123456");
        login.setSenha("novaSenha");

        assertThat(login.getId()).isNull();

        // Act
        LoginEntity loginSalvo = entityManager.persistAndFlush(login);

        // Assert
        assertThat(loginSalvo.getId()).isNotNull();
        assertThat(loginSalvo.getId()).isInstanceOf(UUID.class);

        entityManager.clear();
        LoginEntity loginRecuperado = entityManager.find(LoginEntity.class, login.getId());

        assertThat(loginRecuperado).isNotNull();
        assertThat(loginRecuperado.getId()).isNotNull();
    }

    @Test
    void deveAtualizarSenhaUsandoMetodo() {
        // Arrange
        LoginEntity login = new LoginEntity();
        login.setMatricula("123456");
        login.setSenha("novaSenha");

        assertThat(login.getSenha()).isEqualTo("novaSenha");

        // Act
        LoginEntity loginSalvo = entityManager.persistAndFlush(login);
        loginSalvo.atualizarSenha("senhaAtualizada");

        LoginEntity loginAtualizado = entityManager.persistAndFlush(loginSalvo);

        entityManager.clear();
        LoginEntity loginRecuperado = entityManager.find(LoginEntity.class, loginAtualizado.getId());

        // Assert
        assertThat(loginRecuperado).isNotNull();
        assertThat(loginRecuperado.getSenha()).isEqualTo("senhaAtualizada");
    }

    @Test
    void deveAtualizarMatriculaUsandoMetodo() {
        // Arrange
        LoginEntity login = new LoginEntity();
        login.setMatricula("123456");
        login.setSenha("novaSenha");

        assertThat(login.getMatricula()).isEqualTo("123456");

        // Act
        LoginEntity loginSalvo = entityManager.persistAndFlush(login);
        loginSalvo.atualizarMatricula("78910");

        LoginEntity loginAtualizado = entityManager.persistAndFlush(loginSalvo);

        entityManager.clear();
        LoginEntity loginRecuperado = entityManager.find(LoginEntity.class, loginAtualizado.getId());

        // Assert
        assertThat(loginRecuperado).isNotNull();
        assertThat(loginRecuperado.getMatricula()).isEqualTo("78910");
    }

    @Test
    void deveLancarExcecaoAoCadastrarMatriculaDuplicada() {
        // Arrange
        LoginEntity login1 = new LoginEntity();
        login1.setMatricula("123456");
        login1.setSenha("novaSenha");

        LoginEntity login2 = new LoginEntity();
        login2.setMatricula("123456");
        login2.setSenha("outraSenha");

        // Act + Assert
        LoginEntity loginSalvo = entityManager.persistAndFlush(login1);

        assertThatThrownBy(() -> {
            entityManager.persistAndFlush(login2);

        }).isInstanceOf(ConstraintViolationException.class);
    }



}
