package br.com.fiapfood.unitario.services;

import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.ILoginRepository;
import br.com.fiapfood.services.LoginService;
import br.com.fiapfood.services.exceptions.LoginSemAcessoException;
import br.com.fiapfood.services.interfaces.ILoginService;
import br.com.fiapfood.services.interfaces.IUsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiapfood.utils.DataGenerator.validLoginEntity;
import static br.com.fiapfood.utils.DataGenerator.validLoginRecordRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private ILoginRepository loginRepository;
    @Mock
    private IUsuarioService usuarioService;
    private ILoginService loginService;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        loginService = new LoginService(loginRepository, usuarioService);
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }

    @Nested
    class Validar{
        @Test
        void devePermitirValidarLogin(){
            // Arrange
            LoginRecordRequest loginRecordRequest = validLoginRecordRequest();
            LoginEntity loginEntity = validLoginEntity();
            UsuarioEntity usuarioEntity = new UsuarioEntity();

            when(loginRepository.buscarPorMatriculaSenha(anyString(), anyString()))
                    .thenReturn(loginEntity);
            when(usuarioService.buscarUsuarioPorIdLogin(loginEntity.getId())).thenReturn(usuarioEntity);

            // Act
            String message = loginService.validar(loginRecordRequest);

            // Assert
            assertThat(message).isEqualTo("Acesso liberado");
            verify(loginRepository, times(1)).buscarPorMatriculaSenha(anyString(), anyString());
            verify(usuarioService, times(1)).buscarUsuarioPorIdLogin(loginEntity.getId());
        }

        @Test
        void deveLancarLoginNaoEncontradoExceptionExceptionSeLoginNaoEncontradoAtravesDeMatriculaESenha(){
            // Arrange
            LoginRecordRequest loginRecordRequest = validLoginRecordRequest();
            LoginEntity loginEntity = validLoginEntity();

            when(loginRepository.buscarPorMatriculaSenha(anyString(), anyString()))
                    .thenThrow(new LoginNaoEncontradoException("Não foi encontrado um usuário com a matrícula e senha informados."));

            // Act & Assert
            LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> {
                loginService.validar(loginRecordRequest);
            });

            assertThat(exception.getMessage()).isEqualTo("Não foi encontrado um usuário com a matrícula e senha informados.");

            verify(loginRepository, times(1)).buscarPorMatriculaSenha(anyString(), anyString());
            verify(usuarioService, never()).buscarUsuarioPorIdLogin(loginEntity.getId());
        }

        @Test
        void deveLancarLoginSemAcessoExceptionSeSeUsuarioNaoEncontradoAtravesDoLoginId(){
            // Arrange
            LoginRecordRequest loginRecordRequest = validLoginRecordRequest();
            LoginEntity loginEntity = validLoginEntity();
            UsuarioEntity usuarioEntity = new UsuarioEntity();

            when(loginRepository.buscarPorMatriculaSenha(anyString(), anyString()))
                    .thenReturn(loginEntity);
            when(usuarioService.buscarUsuarioPorIdLogin(loginEntity.getId())).thenThrow(new LoginSemAcessoException("O usuário não possui permissão de acesso."));

            // Act & Assert
            LoginSemAcessoException exception = assertThrows(LoginSemAcessoException.class, () -> {
                loginService.validar(loginRecordRequest);
            });

            assertThat(exception.getMessage()).isEqualTo("O usuário não possui permissão de acesso.");

            verify(loginRepository, times(1)).buscarPorMatriculaSenha(anyString(), anyString());
            verify(usuarioService, times(1)).buscarUsuarioPorIdLogin(loginEntity.getId());
        }
    }

    @Nested
    class TrocarSenha{
        @Test
        void deveTrocarSenhaComSucesso(){
            // Arrange
            LoginEntity loginEntity = validLoginEntity();
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            String matricula = "us0001";
            String senhaNova = "123";

            when(loginRepository.buscarPorMatricula(anyString())).thenReturn(loginEntity);
            when(usuarioService.buscarUsuarioPorIdLogin(loginEntity.getId())).thenReturn(usuarioEntity);

            ArgumentCaptor<LoginEntity> loginCaptor = ArgumentCaptor.forClass(LoginEntity.class);

            // Act
            loginService.trocarSenha(matricula, senhaNova);

            // Assert
            verify(loginRepository, times(1)).buscarPorMatricula(matricula);
            verify(usuarioService, times(1)).buscarUsuarioPorIdLogin(loginEntity.getId());
            verify(loginRepository,times(1)).salvar(loginCaptor.capture());

            LoginEntity loginSalvo = loginCaptor.getValue();
            assertEquals(senhaNova, loginSalvo.getSenha());
        }

        @Test
        void deveLancarLoginNaoEncontradoExceptionSeLoginNaoEncontradoPorMatricula(){
            // Arrange
            LoginEntity loginEntity = validLoginEntity();
            String matricula = "us0001";
            String senhaNova = "123";

            when(loginRepository.buscarPorMatricula(anyString())).thenThrow(new LoginNaoEncontradoException("Não foi encontrado um usuário com a matrícula e senha informados."));

            // Act & Assert
            LoginNaoEncontradoException exception = assertThrows(LoginNaoEncontradoException.class, () -> {
                loginService.trocarSenha(matricula, senhaNova);
            });

            assertThat(exception.getMessage()).isEqualTo("Não foi encontrado um usuário com a matrícula e senha informados.");

            verify(loginRepository, times(1)).buscarPorMatricula(matricula);
            verifyNoInteractions(usuarioService);
            verify(loginRepository,times(0)).salvar(loginEntity);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeUsuarioNaoEncontradoPorIdLogin(){
            // Arrange
            LoginEntity loginEntity = validLoginEntity();
            String matricula = "us0001";
            String senhaNova = "123";

            when(loginRepository.buscarPorMatricula(anyString())).thenReturn(loginEntity);
            when(usuarioService.buscarUsuarioPorIdLogin(loginEntity.getId())).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> {
                loginService.trocarSenha(matricula, senhaNova);
            });

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");

            verify(loginRepository, times(1)).buscarPorMatricula(matricula);
            verify(usuarioService, times(1)).buscarUsuarioPorIdLogin(loginEntity.getId());
            verify(loginRepository,times(0)).salvar(loginEntity);
        }
    }

    @Nested
    class MatriculaJaCadastrada{
        @Test
        void deveRetornarTrueSeMatriculaJaCadastrada(){
            // Arrange
            String matricula = "us0001";

            when(loginRepository.matriculaJaCadastrada(matricula)).thenReturn(true);

            // Act
            boolean response = loginService.matriculaJaCadastrada(matricula);

            // Assert
            assertThat(response).isTrue();
            verify(loginRepository,  times(1)).matriculaJaCadastrada(matricula);
        }

        @Test
        void deveRetornarFalseSeMatriculaNaoCadastrada(){
            // Arrange
            String matricula = "us0001";

            when(loginRepository.matriculaJaCadastrada(matricula)).thenReturn(false);

            // Act
            boolean response = loginService.matriculaJaCadastrada(matricula);

            // Assert
            assertThat(response).isFalse();
            verify(loginRepository,  times(1)).matriculaJaCadastrada(matricula);
        }
    }
}
