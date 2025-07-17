package br.com.fiapfood.integracao.repositories;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.impl.LoginRepository;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validInexistenteLoginEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LoginRepositoryIT {

    @Autowired
    private LoginRepository loginRepository;

    private LoginEntity loginEntity;

    @BeforeEach
    void setUp() {
        loginEntity = validInexistenteLoginEntity();
    }

    @Test
    void deveBuscarPorId() {
        // Arrange
        UUID loginIdExistente = UUID.fromString("c303266f-9d32-4dde-8f4c-d8ee13b24ae9");

        // Act
        LoginEntity loginEncontrado = loginRepository.buscarPorId(loginIdExistente);

        // Assert
        assertThat(loginEncontrado).isNotNull();
        assertThat(loginEncontrado.getId()).isEqualTo(loginIdExistente);
    }

    @Test
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        // Arrange
        UUID loginIdInexistente = UUID.randomUUID();

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorId(loginIdInexistente))
                .isInstanceOf(LoginNaoEncontradoException.class)
                .hasMessageContaining(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
    }

    @Test
    void deveBuscarPorMatriculaESenha() {
        // Arrange
        UUID loginIdExistente = UUID.fromString("c303266f-9d32-4dde-8f4c-d8ee13b24ae9");
        LoginEntity loginExistente = loginRepository.buscarPorId(loginIdExistente);

        // Act
        LoginEntity loginEncontrado = loginRepository
                .buscarPorMatriculaSenha(loginExistente.getMatricula(), loginExistente.getSenha());

        // Assert
        assertThat(loginEncontrado).isNotNull();
        assertThat(loginEncontrado.getId()).isEqualTo(loginIdExistente);
        assertThat(loginEncontrado.getSenha()).isEqualTo(loginExistente.getSenha());
        assertThat(loginEncontrado.getMatricula()).isEqualTo(loginExistente.getMatricula());
    }

    @Test
    void deveLancarExcecaoAoBuscarPorMatriculaESenhaInexistente() {
        // Arrange
        String matriculaInexistente = "us9999";
        String senhaInexistente = "senhaInexistente";

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorMatriculaSenha(matriculaInexistente, senhaInexistente))
                .isInstanceOf(LoginNaoEncontradoException.class)
                .hasMessageContaining(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
    }

    @Test
    void deveBuscarPorMatricula() {
        // Arrange
        UUID loginIdExistente = UUID.fromString("c303266f-9d32-4dde-8f4c-d8ee13b24ae9");
        LoginEntity loginExistente = loginRepository.buscarPorId(loginIdExistente);

        // Act
        LoginEntity loginEncontrado = loginRepository.buscarPorMatricula(loginExistente.getMatricula());

        // Assert
        assertThat(loginEncontrado).isNotNull();
        assertThat(loginEncontrado.getId()).isEqualTo(loginIdExistente);
        assertThat(loginEncontrado.getSenha()).isEqualTo(loginExistente.getSenha());
        assertThat(loginEncontrado.getMatricula()).isEqualTo(loginExistente.getMatricula());
    }

    @Test
    void deveLancarExcecaoAoBuscarPorMatriculaInexistente() {
        // Arrange
        String matriculaInexistente = "us9999";

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorMatricula(matriculaInexistente))
                .isInstanceOf(LoginNaoEncontradoException.class)
                .hasMessageContaining(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_LOGIN_NAO_ENCONTRADO));
    }

    @Test
    void deveVerificarQueMatriculaNaoEstaCadastrada() {
        // Arrange
        String matriculaNaoCadastrada = "us9999";

        // Act
        boolean resultado = loginRepository.matriculaJaCadastrada(matriculaNaoCadastrada);

        // Assert
        assertThat(resultado).isFalse();
    }

    @Test
    void deveSalvarNovoLogin() {
        // Act
        loginRepository.salvar(loginEntity);

        // Assert
        LoginEntity loginEncontrado = loginRepository.buscarPorId(loginEntity.getId());
        assertThat(loginEncontrado).isNotNull();
        assertThat(loginEncontrado.getId()).isEqualTo(loginEntity.getId());
        assertThat(loginEncontrado.getSenha()).isEqualTo(loginEntity.getSenha());
        assertThat(loginEncontrado.getMatricula()).isEqualTo(loginEntity.getMatricula());

        boolean matriculaJaCadastrada = loginRepository.matriculaJaCadastrada(loginEncontrado.getMatricula());
        assertThat(matriculaJaCadastrada).isTrue();
    }

    @Test
    void deveAtualizarLoginComSucesso() {
        // Arrange
        loginRepository.salvar(loginEntity);
        UUID loginId = loginEntity.getId();
        String novaMatricula = "us12345";
        String novaSenha = "senhaAtualizada";

        // Act
        loginEntity.setMatricula(novaMatricula);
        loginEntity.setSenha(novaSenha);
        loginRepository.salvar(loginEntity);

        // Assert
        LoginEntity loginEncontrado = loginRepository.buscarPorId(loginId);
        assertThat(loginEncontrado).isNotNull();
        assertThat(loginEncontrado.getId()).isEqualTo(loginId);
        assertThat(loginEncontrado.getSenha()).isEqualTo(novaSenha);
        assertThat(loginEncontrado.getMatricula()).isEqualTo(novaMatricula);
    }

}
