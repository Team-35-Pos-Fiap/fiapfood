package br.com.fiapfood.unitario.entities;

import br.com.fiapfood.entities.db.LoginEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginEntityTest {

    private LoginEntity loginEntity;

    @BeforeEach
    void setUp() {
        loginEntity = new LoginEntity();
        loginEntity.setId(UUID.randomUUID());
        loginEntity.setMatricula("12345");
        loginEntity.setSenha("senhaInicial");
    }

    @Test
    void deveAtualizarSenha() {
        // Arrange
        String novaSenha = "novaSenha";

        // Act
        loginEntity.atualizarSenha(novaSenha);

        // Assert
        assertThat(loginEntity.getSenha())
                .isEqualTo(novaSenha)
                .isNotEqualTo("senhaInicial");
    }

    @Test
    void deveAtualizarMatricula() {
        // Arrange
        String novaMatricula = "678910";

        // Act
        loginEntity.atualizarMatricula(novaMatricula);

        // Assert
        assertThat(loginEntity.getMatricula())
                .isEqualTo(novaMatricula)
                .isNotEqualTo("12345");

    }

}
