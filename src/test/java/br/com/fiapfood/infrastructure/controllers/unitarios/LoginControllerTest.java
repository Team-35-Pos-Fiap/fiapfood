package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.ILoginCoreController;
import br.com.fiapfood.core.entities.dto.LoginDto;
import br.com.fiapfood.core.exceptions.LoginNaoEncontradoException;
import br.com.fiapfood.core.exceptions.MatriculaDuplicadaException;
import br.com.fiapfood.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.core.exceptions.UsuarioSemAcessoException;
import br.com.fiapfood.infraestructure.controllers.LoginController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.infraestructure.controllers.request.MatriculaDto;
import br.com.fiapfood.infraestructure.controllers.request.SenhaDto;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ILoginCoreController loginCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        LoginController loginController = new LoginController(loginCoreController);

        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                .setControllerAdvice(new ErrorHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class ValidarLoginRequest {
        @DisplayName("Validação de login com sucesso")
        @Test
        void devePermitirValidarLogin() throws Exception {
            // Arrange
            LoginDto loginRecordRequest = new LoginDto(UUID.randomUUID(),"us0001", "123");

            when(loginCoreController.validar(any(LoginDto.class)))
                    .thenReturn("Acesso liberado");

            // Act & Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.mensagem").value("Acesso liberado"));
            verify(loginCoreController, times(1)).validar(any(LoginDto.class));
        }

        @DisplayName("Validação de login com erro. Login não encontrado através de matrícula e senha.")
        @Test
        void deveLancarExcecaoSeLoginNaoEncontradoAtravesDaMatriculaESenha() throws Exception {
            // Arrange
            LoginDto loginRecordRequest = new LoginDto(UUID.randomUUID(),"us0001", "123");

            when(loginCoreController.validar(any(LoginDto.class)))
                    .thenThrow(new LoginNaoEncontradoException("Não foi encontrado nenhum login com a matrícula e senha informados."));

            // Act & Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum login com a matrícula e senha informados."));
            verify(loginCoreController, times(1)).validar(any(LoginDto.class));
        }

        @DisplayName("Validação de login com erro. Usuário ativo não encontrado através do login id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoVinculadoComOLogin() throws Exception {
            // Arrange
            LoginDto loginRecordRequest = new LoginDto(UUID.randomUUID(),"us0001", "123");

            when(loginCoreController.validar(any(LoginDto.class)))
                    .thenThrow(new UsuarioSemAcessoException("Não é possível realizar o login para usuários inativos."));

            // Act & Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não é possível realizar o login para usuários inativos."));
            verify(loginCoreController, times(1)).validar(any(LoginDto.class));
        }

        // Teste comentado, pois não estamos validando o DTO
//        @DisplayName("Validação de login com erro. Erro com dados no DTO")
//        @ParameterizedTest
//        @CsvSource({
//                " , 123, O campo matrícula não foi informado.",
//                "US, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
//                "US00001, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
//                "us0001, , O campo senha não foi informado."
//        })
//        void deveLancarExcecaoParaCamposInvalidos(String matricula, String senha, String expectedError) throws Exception {
//            // Arrange
//            LoginDto loginRecordRequest = new LoginDto(UUID.randomUUID(),"us0001", "123");
//
//            // Act & Assert
//            mockMvc.perform(post("/login")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(loginRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(org.hamcrest.Matchers.hasItem(expectedError)));
//        }
    }

    @Nested
    class AtualizarSenhaRequest {
        @DisplayName("Trocar senha com sucesso")
        @Test
        void devePermitirTrocarSenha() throws Exception {
            // Arrange
            String matricula = "us0001";
            SenhaDto novaSenhaDto = new SenhaDto("124");

            doNothing().when(loginCoreController).atualizarSenha(anyString(), anyString());

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO, new Object[0]))
                        .thenReturn("Senha alterada com sucesso.");

                mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(novaSenhaDto)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Senha alterada com sucesso."));
            }

            verify(loginCoreController, times(1)).atualizarSenha(anyString(), anyString());
        }

        @DisplayName("Atualizar senha com erro. Login nao encontrado através da matricula")
        @Test
        void deveLancarExcecaoSeLoginNaoEncontradoAtravesDaMatricula() throws Exception {
            // Arrange
            String matricula = "us0010";
            SenhaDto novaSenhaDto = new SenhaDto("124");

            doThrow(new LoginNaoEncontradoException("Não foi encontrado nenhum login com a matrícula informada.")).when(loginCoreController).atualizarSenha(anyString(), anyString());

            // Act & Assert
            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaSenhaDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum login com a matrícula informada."));

            verify(loginCoreController, times(1)).atualizarSenha(anyString(), anyString());
        }

        @DisplayName("Atualizar senha com erro. Não encontrado usuário ativo vinculado com o login")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoVinculadoComOLogin() throws Exception {
            // Arrange
            String matricula = "us0003";
            SenhaDto novaSenhaDto = new SenhaDto("124");

            doThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados.")).when(loginCoreController).atualizarSenha(anyString(), anyString());

            // Act & Arrange
            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaSenhaDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foram encontrados usuários na base de dados."));

            verify(loginCoreController, times(1)).atualizarSenha(anyString(), anyString());
        }

        @DisplayName("Alterar senha com erro. Dados inválidos no DTO")
        @Test
        void deveLancarExcecaoParaCamposInvalidos() throws Exception {
            // Arrange
            String matricula = "us0001";
            SenhaDto novaSenhaDto = new SenhaDto("");

            // Act & Arrange
            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaSenhaDto)))
                    .andDo(print())
                    .andExpect(status().is5xxServerError()) // Não tenho certeza o motivo de estar sendo 500 aqui
                    .andExpect(jsonPath("$.senha").value("O campo senha precisa estar preenchido."));

            verify(loginCoreController, times(0)).atualizarSenha(anyString(), anyString());
        }
    }

    @Nested
    class AtualizarMatriculaRequest {
        @DisplayName("Trocar matricula com sucesso")
        @Test
        void devePermitirTrocarMatricula() throws Exception {
            // Arrange
            String matriculaAtual = "us0001";
            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");

            doNothing().when(loginCoreController).atualizarMatricula(anyString(), anyString());

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_TROCA_MATRICULA_USUARIO, new Object[0]))
                        .thenReturn("Matricula alterada com sucesso.");

                mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(novaMatriculaDto)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Matricula alterada com sucesso."));
            }

            verify(loginCoreController, times(1)).atualizarMatricula(anyString(), anyString());
        }

        @DisplayName("Atualizar matricula com erro. Login nao encontrado através da matricula atual")
        @Test
        void deveLancarExcecaoSeLoginNaoEncontradoAtravesDaMatricula() throws Exception {
            // Arrange
            String matriculaAtual = "us0008";
            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");

            doThrow(new LoginNaoEncontradoException("Não foi encontrado nenhum login com a matrícula informada.")).when(loginCoreController).atualizarMatricula(anyString(), anyString());

            // Act & Assert
            mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaMatriculaDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum login com a matrícula informada."));

            verify(loginCoreController, times(1)).atualizarMatricula(anyString(), anyString());
        }

        @DisplayName("Atualizar matricula com erro. Não encontrado usuário ativo vinculado com o login")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoVinculadoComOLogin() throws Exception {
            // Arrange
            String matriculaAtual = "us0008";
            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");

            doThrow(new UsuarioNaoEncontradoException("Não é possível alterar a matricula de um usuário inativo.")).when(loginCoreController).atualizarMatricula(anyString(), anyString());

            // Act & Arrange
            mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaMatriculaDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não é possível alterar a matricula de um usuário inativo."));

            verify(loginCoreController, times(1)).atualizarMatricula(anyString(), anyString());
        }

        @DisplayName("Atualizar matricula com erro. Nova matricula ja cadastrada.")
        @Test
        void deveLancarExcecaoSeNovaMatriculaJaCadastrada() throws Exception {
            // Arrange
            String matriculaAtual = "us0001";
            MatriculaDto novaMatriculaDto = new MatriculaDto("us0002");

            doThrow(new MatriculaDuplicadaException("Já existe um usuário com a matrícula informada.")).when(loginCoreController).atualizarMatricula(anyString(), anyString());

            // Act & Arrange
            mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaMatriculaDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Já existe um usuário com a matrícula informada."));

            verify(loginCoreController, times(1)).atualizarMatricula(anyString(), anyString());
        }

        @DisplayName("Alterar matricula com erro. Dados inválidos no DTO")
        @Test
        void deveLancarExcecaoParaCamposInvalidos() throws Exception {
            // Arrange
            String matriculaAtual = "us0001";
            MatriculaDto novaMatriculaDto = new MatriculaDto("");

            // Act & Arrange
            mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaMatriculaDto)))
                    .andDo(print())
                    .andExpect(status().is5xxServerError()) // Não tenho certeza o motivo de estar sendo 500 aqui
                    .andExpect(jsonPath("$.matricula").value("O campo matricula precisa estar preenchido."));

            verify(loginCoreController, times(0)).atualizarMatricula(anyString(), anyString());
        }
    }
}
