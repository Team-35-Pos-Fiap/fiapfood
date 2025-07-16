package br.com.fiapfood.unitario.repositories;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.impl.LoginRepository;
import br.com.fiapfood.repositories.interfaces.jpa.ILoginJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static br.com.fiapfood.utils.DataGenerator.validLoginEntity;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginRepositoryTest {

    @Mock
    private ILoginJpaRepository loginJpaRepository;

    private LoginRepository loginRepository;

    AutoCloseable mock;

    private UUID loginId;

    private LoginEntity loginEntity;

    private MockedStatic<MensagensUtil> mensagemUtilMock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemUtilMock = mockStatic(MensagensUtil.class);
        loginRepository = new LoginRepository(loginJpaRepository);
        loginId = UUID.randomUUID();
        loginEntity = validLoginEntity();

        mensagemUtilMock.when(() -> MensagensUtil.recuperarMensagem(anyString()))
                .thenReturn("Não foi encontrado um usuário com a matrícula e senha informados.");
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
        mensagemUtilMock.close();
    }

    @Test
    void devePermitirBuscarPorId(){
        // Arrange
        loginEntity.setId(loginId);
        when(loginJpaRepository.findById(any(UUID.class))).thenReturn(Optional.of(loginEntity));

        // Act
        var loginEncontrado = loginRepository.buscarPorId(loginId);

        // Assert
        assertThat(loginEncontrado)
                .isNotNull()
                .isEqualTo(loginEntity);
        verify(loginJpaRepository, times(1)).findById(loginId);
    }

    @Test
    void deveLancarLoginNaoEncontradoExecptionSeNaoEcontrarLoginEmBuscarPorId(){
        // Arrange
        when(loginJpaRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorId(loginId))
            .isInstanceOf(LoginNaoEncontradoException.class)
            .hasMessage("Não foi encontrado um usuário com a matrícula e senha informados.");
        verify(loginJpaRepository, times(1)).findById(loginId);
    }

    @Test
    void deveBuscarPorMatriculaESenha() {
        // Arrange
        when(loginJpaRepository.findByMatriculaAndSenha(anyString(), anyString()))
                .thenReturn(Optional.of(loginEntity));

        // Act
        var loginEncontrado = loginRepository.buscarPorMatriculaSenha(loginEntity.getMatricula(), loginEntity.getSenha());

        // Assert
        assertThat(loginEncontrado)
                .isNotNull()
                .isEqualTo(loginEntity);
        verify(loginJpaRepository, times(1)).findByMatriculaAndSenha(anyString(), anyString());
    }

    @Test
    void deveLancarLoginNaoEncontradoExceptionSeNaoEcontrarLoginEmBuscarPorMatriculaSenha() {
        // Arrange
        when(loginJpaRepository.findByMatriculaAndSenha(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorMatriculaSenha("us0123", "123"))
                .isInstanceOf(LoginNaoEncontradoException.class)
                .hasMessageContaining("Não foi encontrado um usuário com a matrícula e senha informados.");
    }

    @Test
    void deveBuscarPorMatricula() {
        // Arrange
        when(loginJpaRepository.findByMatricula(anyString())).thenReturn(Optional.of(loginEntity));

        // Act
        var loginEncontrado = loginRepository.buscarPorMatricula("us0123");

        // Assert
        assertThat(loginEncontrado)
                .isNotNull()
                .isEqualTo(loginEntity);
        verify(loginJpaRepository, times(1)).findByMatricula("us0123");
    }

    @Test
    void deveLancarLoginNaoEncontradoExceptionSeNaoEcontrarLoginEmbuscarPorMatricula() {
        // Arrange
        when(loginJpaRepository.findByMatricula(anyString())).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> loginRepository.buscarPorMatricula("us0123"))
                .isInstanceOf(LoginNaoEncontradoException.class)
                .hasMessageContaining("Não foi encontrado um usuário com a matrícula e senha informados.");
    }

    @Test
    void devePermitirSalvarLogin() {
        // Act
        loginRepository.salvar(loginEntity);

        // Assert
        assertThat(loginEntity).isNotNull();
        verify(loginJpaRepository, times(1)).save(loginEntity);
    }

    @Test
    void deveChecarSeMatriculaJaEstaCadastrada() {
        // Arrange
        when(loginJpaRepository.existsByMatricula(anyString())).thenReturn(true);

        // Act
        var matriculaCadastrada = loginRepository.matriculaJaCadastrada("us0123");

        // Assert
        assertThat(matriculaCadastrada).isTrue();
        verify(loginJpaRepository, times(1)).existsByMatricula("us0123");
    }
}
