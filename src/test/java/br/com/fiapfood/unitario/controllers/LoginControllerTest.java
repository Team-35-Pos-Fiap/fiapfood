package br.com.fiapfood.unitario.controllers;

import br.com.fiapfood.controllers.LoginController;
import br.com.fiapfood.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.request.SenhaRecordRequest;
import br.com.fiapfood.repositories.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.services.exceptions.LoginSemAcessoException;
import br.com.fiapfood.services.interfaces.ILoginService;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ILoginService loginService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        LoginController loginController = new LoginController(loginService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setControllerAdvice(new ErrorHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }

    @Nested
    class validarLoginRequest{

        @Test
        void devePermitirValidarLogin() throws Exception {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0001", "123");

            when(loginService.validar(any(LoginRecordRequest.class)))
                    .thenReturn("Acesso liberado");

            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.mensagem").value("Acesso liberado"));
            verify(loginService, times(1)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeLoginNaoEncontradoAtravesDaMatriculaESenha() throws Exception {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0001", "123");

            when(loginService.validar(any(LoginRecordRequest.class)))
                    .thenThrow(new LoginNaoEncontradoException("Não foi encontrado um usuário com a matrícula e senha informados."));
            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado um usuário com a matrícula e senha informados."));
            verify(loginService, times(1)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeMatriculaEmBrancoNosDadosDeLogin() throws Exception {
            // Arrange
            LoginRecordRequest LoginRecordRequestMissingMatricula = new LoginRecordRequest(null, "123");

            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(LoginRecordRequestMissingMatricula)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.matricula").value("O campo matrícula não foi informado."));
            verify(loginService, times(0)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeMatriculaMenorDoQue3LetrasNosDadosDeLogin() throws Exception {
            // Arrange
            LoginRecordRequest LoginRecordRequestMissingMatricula = new LoginRecordRequest("US", "123");

            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(LoginRecordRequestMissingMatricula)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.matricula").value("O campo matrícula precisa ter entre 3 e 6 caracteres."));
            verify(loginService, times(0)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeMatriculaMaiorDoQue6LetrasNosDadosDeLogin() throws Exception {
            // Arrange
            LoginRecordRequest LoginRecordRequestMissingMatricula = new LoginRecordRequest("US00001", "123");

            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(LoginRecordRequestMissingMatricula)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.matricula").value("O campo matrícula precisa ter entre 3 e 6 caracteres."));
            verify(loginService, times(0)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeSenhaEmBrancoNosDadosDeLogin() throws Exception {
            // Arrange
            LoginRecordRequest LoginRecordRequestMissingSenha = new LoginRecordRequest("us0001", "");

            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(LoginRecordRequestMissingSenha)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            verify(loginService, times(0)).validar(any(LoginRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoVinculadoComOLogin() throws Exception {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0001", "123");

            when(loginService.validar(any(LoginRecordRequest.class)))
                    .thenThrow(new LoginSemAcessoException("O usuário não possui permissão de acesso."));
            // Act

            // Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.mensagem").value("O usuário não possui permissão de acesso."));
            verify(loginService, times(1)).validar(any(LoginRecordRequest.class));
        }
    }

    @Nested
    class validarAtulizarSenhaRequest{
        @Test
        void devePermitirTrocarSenha() throws Exception {
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            doNothing().when(loginService).trocarSenha(anyString(), anyString());

            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO, new Object[0]))
                        .thenReturn("Senha alterada com sucesso.");

                mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(senhaRecordRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Senha alterada com sucesso."));
            }

            verify(loginService, times(1)).trocarSenha(anyString(), anyString());
        }

        @Test
        void deveLancarExcecaoSeLoginNaoEncontradoAtravesDaMatricula() throws Exception {
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            doThrow(new LoginNaoEncontradoException("Não foi encontrado um usuário com a matrícula e senha informados.")).when(loginService).trocarSenha(anyString(), anyString());

                mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(senhaRecordRequest)))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.mensagem").value("Não foi encontrado um usuário com a matrícula e senha informados."));

            verify(loginService, times(1)).trocarSenha(anyString(), anyString());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoVinculadoComOLogin() throws Exception {
            // Arrange
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            doThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados.")).when(loginService).trocarSenha(anyString(), anyString());

            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(senhaRecordRequest)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foram encontrados usuários na base de dados."));

            verify(loginService, times(1)).trocarSenha(anyString(), anyString());
        }

        @Test
        void deveLancarExcecaoSeNovaSenhaEmBranco() throws Exception {
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("");

            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(senhaRecordRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.senha").value("O campo senha precisa estar preenchido."));

            verify(loginService, times(0)).trocarSenha(anyString(), anyString());
        }
    }
}
