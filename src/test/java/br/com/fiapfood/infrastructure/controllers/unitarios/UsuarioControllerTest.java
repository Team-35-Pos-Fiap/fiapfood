package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.IUsuarioCoreController;
import br.com.fiapfood.core.exceptions.*;
import br.com.fiapfood.infraestructure.controllers.UsuarioController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.login.LoginDto;
import br.com.fiapfood.infraestructure.controllers.request.paginacao.PaginacaoDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.CadastrarUsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioPaginacaoDto;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.UUID;

import static br.com.fiapfood.utils.DtoDataGenerator.*;
import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUsuarioCoreController usuarioCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() throws Exception {
        mock = MockitoAnnotations.openMocks(this);
        UsuarioController usuarioController = new UsuarioController(usuarioCoreController);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
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
    class CadastrarUsuarioRequest {
        @DisplayName("Cadastrar novo usuário com sucesso")
        @Test
        void devePermitirCadastrarUsuarioComSucesso() throws Exception {
            // Arrange
            CadastrarUsuarioDto dadosUsuario = cadastrarUsuarioDtoValido();

            doNothing().when(usuarioCoreController).cadastrar(any(CadastrarUsuarioDto.class));

            //Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuario)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            verify(usuarioCoreController, times(1)).cadastrar(dadosUsuario);
        }

        @DisplayName("Cadastrar novo usuário com erro. Email já cadastrado")
        @Test
        void deveLancarExcecaoSeEmailJaCadastrado() throws Exception {
            // Arrange
            CadastrarUsuarioDto dadosUsuario = cadastrarUsuarioDtoValido();

            doThrow(new EmailDuplicadoException("Já existe um usuário com o email informado.")).when(usuarioCoreController).cadastrar(any(CadastrarUsuarioDto.class));

            //Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuario)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Já existe um usuário com o email informado."));
            verify(usuarioCoreController, times(1)).cadastrar(dadosUsuario);
        }

        @DisplayName("Cadastrar novo usuário com erro. Matricula já cadastrada")
        @Test
        void deveLancarExcecaoSeMatriculaJaCadastrado() throws Exception {
            // Arrange
            CadastrarUsuarioDto dadosUsuario = cadastrarUsuarioDtoValido();

            doThrow(new MatriculaDuplicadaException("Já existe um usuário com a matrícula informada.")).when(usuarioCoreController).cadastrar(any(CadastrarUsuarioDto.class));

            //Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuario)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Já existe um usuário com a matrícula informada."));
            verify(usuarioCoreController, times(1)).cadastrar(dadosUsuario);
        }

        @DisplayName("Cadastrar novo usuário com erro. Dados do usuário inválidos no DTO")
        @ParameterizedTest
        @CsvSource({
                " , thiago@fiapfood.com, 1, O campo nome precisa estar preenchido.",
                "Thiago Motta, , 1, O campo email precisa estar preenchido.",
                "Thiago Motta, thiago@fiapfood.com, ,  O campo perfil precisa estar preenchido.",
                "Thiago Motta, thiago@fiapfood.com, -1,  O campo perfil precisa ter valor maior do que 0."
        })
        void deveLancarExcecaoParaCamposDeUsuarioInvalidos(String nome, String email, Integer perfil, String expectedError) throws Exception {
            // Arrange
            CadastrarUsuarioDto dadosUsuarioInvalidos = new CadastrarUsuarioDto(nome, perfil, loginDtoValido(), email,  dadosEnderecoDtoValido());

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuarioInvalidos)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioCoreController, times(0)).cadastrar(any(CadastrarUsuarioDto.class));
        }

        @DisplayName("Cadastrar novo usuário com erro. Dados do endereço inválidos no DTO")
        @ParameterizedTest
        @CsvSource({
                " , 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, O campo cidade precisa ser informado.",
                "São Gonçalo, , Nova Cidade, Rua Aquidabã, Rio de Janeiro, O campo cep precisa ser informado.",
                "São Gonçalo, 24455450, , Rua Aquidabã, Rio de Janeiro, O campo bairro precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, , Rio de Janeiro, O campo endereco precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, , O campo estado precisa ser informado.",
        })
        void deveLancarExcecaoParaCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, String expectedError) throws Exception {
            // Arrange
            DadosEnderecoDto dadosEnderecoInvalidos = new DadosEnderecoDto(
                    cidade,
                    cep,
                    bairro,
                    endereco,
                    estado,
                    79,
                    "Casa 8"
            );
            CadastrarUsuarioDto dadosUsuarioInvalidos = new CadastrarUsuarioDto("John Doe", 1, loginDtoValido(), "john.doe@email.com", dadosEnderecoInvalidos);

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuarioInvalidos)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioCoreController, times(0)).cadastrar(any(CadastrarUsuarioDto.class));
        }

        @DisplayName("Cadastrar novo usuário com erro. Dados do login inválidos no DTO")
        @ParameterizedTest
        @CsvSource({
                " , 123, O campo matricula precisa ser informado.",
                "us0001, , O campo senha precisa ser informado."
        })
        void deveLancarExcecaoParaCamposDeLoginInvalidos(String matricula, String senha, String expectedError) throws Exception {
            // Arrange
            LoginDto dadosLoginInvalidos = new LoginDto(
                    null,
                    matricula,
                    senha
            );
            CadastrarUsuarioDto dadosUsuarioInvalidos = new CadastrarUsuarioDto("John Doe", 1, dadosLoginInvalidos, "john.doe@email.com", dadosEnderecoDtoValido());

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuarioInvalidos)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioCoreController, times(0)).cadastrar(any(CadastrarUsuarioDto.class));
        }

        // Nao estamos fazendo essa validacao na use case
        @DisplayName("Cadastrar novo usuário com erro. Perfil informado não cadastrado.")
        @Test
        void deveLancarExcecaoSePerfilNaoCadstrado() throws Exception {
            // Arrange
            CadastrarUsuarioDto dadosUsuarioInvalidos = new CadastrarUsuarioDto("John Doe", 10, loginDtoValido(), "john.doe@email.com", dadosEnderecoDtoValido());

            doThrow(new PerfilNaoEncontradoException("Perfil não encontrado na base de dados.")).when(usuarioCoreController).cadastrar(any(CadastrarUsuarioDto.class));

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(dadosUsuarioInvalidos)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem("Perfil não encontrado na base de dados.")));
            verify(usuarioCoreController, times(1)).cadastrar(any(CadastrarUsuarioDto.class));
        }
    }

    @Nested
    class InativarUsuarioRequest {
        @DisplayName("Inativar usuário com sucesso.")
        @Test
        void devePermitirInativarUsuarioComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(usuarioCoreController).inativar(any(UUID.class));

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_INATIVACAO_USUARIO, new Object[0]))
                        .thenReturn("Usuário inativado com sucesso.");

                mockMvc.perform(patch("/usuarios/{id}/status/inativa", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Usuário inativado com sucesso."));
            }
            verify(usuarioCoreController, times(1)).inativar(any(UUID.class));
        }

        @DisplayName("Inativar usuário com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).inativar(any(UUID.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/status/inativa", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o id informado."));
            verify(usuarioCoreController, times(1)).inativar(any(UUID.class));
        }

        @DisplayName("Inativar usuário com erro. Usuário já está inativo.")
        @Test
        void deveLancarExcecaoSeUsuarioJaEstiverInativo() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível inativar o usuário pois ele já se encontra inativo.")).when(usuarioCoreController).inativar(any(UUID.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/status/inativa", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não é possível inativar o usuário pois ele já se encontra inativo."));
            verify(usuarioCoreController, times(1)).inativar(any(UUID.class));
        }
    }

    @Nested
    class AtivarUsuarioRequest {
        @DisplayName("Ativar usuário com sucesso.")
        @Test
        void devePermitirAtivarUsuarioComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(usuarioCoreController).reativar(any(UUID.class));

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_REATIVACAO_USUARIO, new Object[0]))
                        .thenReturn("Usuário reativado com sucesso.");

                mockMvc.perform(patch("/usuarios/{id}/status/reativa", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Usuário reativado com sucesso."));
            }
            verify(usuarioCoreController, times(1)).reativar(any(UUID.class));
        }

        @DisplayName("Ativar usuário com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).reativar(any(UUID.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/status/reativa", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o id informado."));
            verify(usuarioCoreController, times(1)).reativar(any(UUID.class));
        }

        @DisplayName("Ativar usuário com erro. Usuário já está ativo.")
        @Test
        void deveLancarExcecaoSeUsuarioJaEstiverAtivo() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível reativar um usuário pois ele já se encontra ativo.")).when(usuarioCoreController).reativar(any(UUID.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/status/reativa", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não é possível reativar um usuário pois ele já se encontra ativo."));
            verify(usuarioCoreController, times(1)).reativar(any(UUID.class));
        }
    }

    @Nested
    class BuscarUsuarioPorIdRequest {

        @DisplayName("Busca usuário por id com sucesso.")
        @Test
        void devePermitirBuscarUsuarioPorId() throws Exception {
            // Arrange
            UsuarioDto usuarioEsperado = usuarioDtoValido();
            UUID id = usuarioEsperado.id();

            when(usuarioCoreController.buscarUsuarioPorId(any(UUID.class)))
                    .thenReturn(usuarioEsperado);

            // Act & Assert
            mockMvc.perform(get("/usuarios/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()))
                    .andExpect(jsonPath("$.nome").value(usuarioEsperado.nome()))
                    .andExpect(jsonPath("$.isAtivo").value(usuarioEsperado.isAtivo()))
                    .andExpect(jsonPath("$.email").value(usuarioEsperado.email()));
            verify(usuarioCoreController, times(1)).buscarUsuarioPorId(any(UUID.class));
        }

        @DisplayName("Busca usuário por id com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            when(usuarioCoreController.buscarUsuarioPorId(any(UUID.class)))
                    .thenThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado."));

            // Act & Assert
            mockMvc.perform(get("/usuarios/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioCoreController, times(1)).buscarUsuarioPorId(any(UUID.class));
        }
    }

    @Nested
    class BuscarTodosUsuariosRequest {
        @DisplayName("Buscar todos usuarios com sucesso")
        @Test
        void deveRetornarListaDeUsuariosPaginada() throws Exception {
            // Arrange
            when(usuarioCoreController.buscarTodos(anyInt()))
                    .thenReturn(new UsuarioPaginacaoDto(new ArrayList<>(), new PaginacaoDto(1, 1, 1)));

            // Act & Assert
            mockMvc.perform(get("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(hasKey("usuarios")))
                    .andExpect(jsonPath("$").value(hasKey("paginacao")));
            verify(usuarioCoreController, times(1)).buscarTodos(anyInt());
        }

        @DisplayName("Buscar todos usuarios com erro. Pagina menor ou igual a zero")
        @Test
        void deveLancarExcecaoSePaginaMenorOuIgualAZero() throws Exception {
            // Arrange
            int pagina = 0;

            // Act & Assert
            mockMvc.perform(get("/usuarios?pagina={pagina}", pagina)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value("O parâmetro página precisa ser maior do que 0."));
            verify(usuarioCoreController, times(0)).buscarTodos(anyInt());
        }

        @DisplayName("Buscar todos usuarios com erro. Pagina nao contem nenhum usuario")
        @Test
        void deveRetornarExcecaoSePaginaForMaiorDoQueOLimite() throws Exception {
            // Arrange
            int pagina = 10;

            when(usuarioCoreController.buscarTodos(anyInt())).thenThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados para a página informada.")); // builds inside static mock

            // Act & Assert
            mockMvc.perform(get("/usuarios?pagina={pagina}", pagina)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.mensagem").value("Não foram encontrados usuários na base de dados para a página informada."));
            verify(usuarioCoreController, times(1)).buscarTodos(anyInt());
        }
    }

//    @Nested
//    class AtualizarEmailRequest {
//
//        @Test
//        void devePermitirAtualizarEmail() throws Exception {
//            // Arrange
//            UUID id = UUID.randomUUID();
//            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");
//
//            doNothing().when(usuarioService).atualizarEmail(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/email", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(emailRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isNoContent());
//            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
//        }
//
//        @Test
//        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
//            // Arrange
//            UUID id = UUID.randomUUID();
//            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");
//
//            doThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados.")).when(usuarioService).atualizarEmail(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/email", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(emailRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.*").value(hasItem("Usuário não encontrado na base de dados.")));
//            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
//        }
//
//        @Test
//        void deveLancarExecaoSeEmailJaCadastrado() throws Exception {
//            // Arrange
//            UUID id = UUID.randomUUID();
//            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");
//
//            doThrow(new EmailDuplicadoException("Já existe um usuário com o email informado.")).when(usuarioService).atualizarEmail(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/email", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(emailRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("Já existe um usuário com o email informado.")));
//            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
//        }
//
//        @Test
//        void deveLancarExcecaoParaCamposInvalidosNoDto() throws Exception {
//            // Arrange
//            UUID id = UUID.randomUUID();
//            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("johndoe");
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/email", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(emailRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$").value(hasKey("email")))
//                    .andExpect(jsonPath("$.*").value(hasItem("O e-mail precisa ser válido")));
//            verify(usuarioService, times(0)).atualizarEmail(any(UUID.class), anyString());
//        }
//    }
}
