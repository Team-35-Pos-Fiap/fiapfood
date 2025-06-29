package br.com.fiapfood.unitario.controllers;

import br.com.fiapfood.controllers.UsuarioController;
import br.com.fiapfood.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.entities.record.request.*;
import br.com.fiapfood.entities.record.response.PaginacaoRecordResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.services.exceptions.EmailDuplicadoException;
import br.com.fiapfood.services.exceptions.PaginaInvalidaException;
import br.com.fiapfood.services.interfaces.IUsuarioService;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

import static br.com.fiapfood.utils.DataGenerator.validEnderecoRecordRequest;
import static br.com.fiapfood.utils.DataGenerator.validLoginRecordRequest;
import static br.com.fiapfood.utils.DataGenerator.validUsuarioRecordResponse;
import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUsuarioService usuarioService;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        UsuarioController usuarioController = new UsuarioController(usuarioService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
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
    class CadastrarUsuarioRequest {
        @Test
        void devePermitirCadstrarUsuario() throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "Thiago Motta",
                    "thiago@fiapfood.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            doNothing().when(usuarioService).cadastrar(any(UsuarioRecordRequest.class));


            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated());
            verify(usuarioService, times(1)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeEmailJaCadastrado() throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "Thiago Motta",
                    "thiago@fiapfood.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            doThrow(EmailDuplicadoException.class).when(usuarioService).cadastrar(any(UsuarioRecordRequest.class));

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
            verify(usuarioService, times(1)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @ParameterizedTest
        @CsvSource({
                " , thiago@fiapfood.com, 1, O campo nome precisa estar preenchido.",
                "Th, thiago@fiapfood.com, 1, O campo nome precisa ter entre 3 e 150 caracteres.",
                "Thiago Motta, , 1, O campo email precisa estar preenchido.",
                "Thiago Motta, thiagofiapfood.com, 1,  O e-mail precisa ser válido",
                "Thiago Motta, thiago@fiapfood.com, ,  É necessário informar o perfil de acesso para o usuário."
        })
        void deveLancarExcecaoParaCamposDeUsuarioInvalidos(String nome, String email, Integer perfil, String expectedError) throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(nome, email, perfil, validEnderecoRecordRequest(), validLoginRecordRequest());

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioService, times(0)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @ParameterizedTest
        @CsvSource({
                " , 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, false, O campo cidade precisa ser informado.",
                "São Gonçalo, , Nova Cidade, Rua Aquidabã, Rio de Janeiro, false, O campo cep precisa ser informado.",
                "São Gonçalo, 24455450, , Rua Aquidabã, Rio de Janeiro, false, O campo bairro precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, , Rio de Janeiro, false, O campo endereco precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, , false, O campo estado precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, , O campo semNumero precisa ser informado.",
        })
        void deveLancarExcecaoParaCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, Boolean semNumero, String expectedError) throws Exception {
            // Arrange
            EnderecoRecordRequest enderecoRecordRequest = new EnderecoRecordRequest(
                    cidade,
                    cep,
                    bairro,
                    endereco,
                    estado,
                    79,
                    "Casa 8",
                    semNumero
            );
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest("Thiago Motta", "thiago@fiapfood.com", 1, enderecoRecordRequest, validLoginRecordRequest());

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioService, times(0)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @ParameterizedTest
        @CsvSource({
                " , 123, O campo matrícula não foi informado.",
                "US, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
                "US00001, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
                "us0001, , O campo senha não foi informado."
        })
        void deveLancarExcecaoParaCamposDeLoginInvalidos(String matricula, String senha, String expectedError) throws Exception {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest(
                    matricula,
                    senha
            );
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest("Thiago Motta", "thiago@fiapfood.com", 1, validEnderecoRecordRequest(), loginRecordRequest);

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioService, times(0)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoParaNomeComMaisDe150Caracteres() throws Exception {
            // Arrange
            String nome151 = "A".repeat(151);
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    nome151,
                    "valid@email.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem("O campo nome precisa ter entre 3 e 150 caracteres.")));
            verify(usuarioService, times(0)).cadastrar(any(UsuarioRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSePerfilNaoCadstrado() throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "Thiago Motta",
                    "thiago@fiapfood.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            doThrow(new PerfilNaoEncontradoException("Perfil não encontrado na base de dados.")).when(usuarioService).cadastrar(any(UsuarioRecordRequest.class));

            // Act & Assert
            mockMvc.perform(post("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(usuarioRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.*").value(hasItem("Perfil não encontrado na base de dados.")));
            verify(usuarioService, times(1)).cadastrar(any(UsuarioRecordRequest.class));
        }
    }

    @Nested
    class InativarUsuarioRequest {
        @Test
        void devePermitirInativarUsuario() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(usuarioService).atualizarStatus(any(UUID.class), anyBoolean());

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_INATIVACAO_USUARIO, new Object[0]))
                        .thenReturn("Usuário inativado com sucesso.");

                mockMvc.perform(delete("/usuarios/{id}/status", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Usuário inativado com sucesso."));
            }
            verify(usuarioService, times(1)).atualizarStatus(any(UUID.class), anyBoolean());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(UsuarioNaoEncontradoException.class).when(usuarioService).atualizarStatus(any(UUID.class), anyBoolean());

            // Act & Assert
            mockMvc.perform(delete("/usuarios/{id}/status", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1)).atualizarStatus(any(UUID.class), anyBoolean());
        }
    }

    @Nested
    class ReativarUsuarioRequest {
        @Test
        void devePermitirReativarUsuario() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(usuarioService).atualizarStatus(any(UUID.class), anyBoolean());

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.SUCESSO_REATIVACAO_USUARIO, new Object[0]))
                        .thenReturn("Usuário reativado com sucesso.");

                mockMvc.perform(patch("/usuarios/{id}/status", id)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.mensagem").value("Usuário reativado com sucesso."));
            }
            verify(usuarioService, times(1)).atualizarStatus(any(UUID.class), anyBoolean());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioInativoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(UsuarioNaoEncontradoException.class).when(usuarioService).atualizarStatus(any(UUID.class), anyBoolean());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/status", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1)).atualizarStatus(any(UUID.class), anyBoolean());
        }
    }

    @Nested
    class BuscarUsuarioPorIdRequest {
        @Test
        void devePermitirBuscarUsuarioPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioRecordResponse usuarioRecordResponse = validUsuarioRecordResponse();
            when(usuarioService.buscarPorId(any(UUID.class))).thenReturn(usuarioRecordResponse);

            // Act & Assert
            mockMvc.perform(get("/usuarios/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(asJsonString(usuarioRecordResponse)));
            verify(usuarioService, times(1)).buscarPorId(any(UUID.class));
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioRecordResponse usuarioRecordResponse = validUsuarioRecordResponse();

            when(usuarioService.buscarPorId(any(UUID.class)))
                    .thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            mockMvc.perform(get("/usuarios/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1)).buscarPorId(any(UUID.class));
        }
    }

    @Nested
    class BuscarTodosUsuariosRequest {
        @Test
        void deveRetornarListaDeUsuariosPaginada() throws Exception {
            // Arrange
            when(usuarioService.buscarUsuarios(anyInt()))
                    .thenReturn(new UsuarioRecordPaginacaoResponse(new ArrayList<>(), new PaginacaoRecordResponse(1, 1, 1)));

            // Act & Assert
            mockMvc.perform(get("/usuarios")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(hasKey("usuarios")))
                    .andExpect(jsonPath("$").value(hasKey("dadosPaginacao")));
            verify(usuarioService, times(1)).buscarUsuarios(anyInt());
        }

        @Test
        void deveLancarExcecaoSePaginaMenorOuIgualAZero() throws Exception {
            // Arrange
            int pagina = 0;

            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.ERRO_PAGINA_INVALIDA, new Object[0]))
                        .thenReturn("O número da página deve ser maior ou igual a 1.");

                when(usuarioService.buscarUsuarios(anyInt()))
                        .thenAnswer(invocation -> {
                            throw new PaginaInvalidaException(); // builds inside static mock
                        });

                // Act & Assert
                mockMvc.perform(get("/usuarios?pagina={pagina}", pagina)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.mensagem").value("O número da página deve ser maior ou igual a 1."));
            }

            verify(usuarioService, times(1)).buscarUsuarios(anyInt());
        }

        @Test
        void deveRetornarExcecaoSePaginaForMaiorDoQueOLimite() throws Exception {
            // Arrange
            int pagina = 10;

            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
                                MensagensUtil.ERRO_USUARIOS_NAO_ENCONTRADOS, new Object[0]))
                        .thenReturn("Não foram encontrados usuários na base de dados.");

                when(usuarioService.buscarUsuarios(anyInt()))
                        .thenAnswer(invocation -> {
                            throw new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados."); // builds inside static mock
                        });

                // Act & Assert
                mockMvc.perform(get("/usuarios?pagina={pagina}", pagina)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.mensagem").value("Não foram encontrados usuários na base de dados."));
            }

            verify(usuarioService, times(1)).buscarUsuarios(anyInt());
        }
    }

    @Nested
    class AtualizarPerfilRequest {
        @Test
        void devePermitirAtualizarPerfil() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(1);

            doNothing().when(usuarioService).atualizarPerfil(any(UUID.class), anyInt());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(perfilRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            verify(usuarioService, times(1)).atualizarPerfil(any(UUID.class), anyInt());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(1);

            doThrow(UsuarioNaoEncontradoException.class).when(usuarioService).atualizarPerfil(any(UUID.class), anyInt());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(perfilRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1)).atualizarPerfil(any(UUID.class), anyInt());
        }

        @Test
        void deveLancarExcecaoSeNaoEcontrarPerfilPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(3);

            doThrow(PerfilNaoEncontradoException.class).when(usuarioService).atualizarPerfil(any(UUID.class), anyInt());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(perfilRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
            verify(usuarioService, times(1)).atualizarPerfil(any(UUID.class), anyInt());
        }

        @Test
        void deveLancarExcecaoParaCamposInvalidosNoDto() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(null);

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(perfilRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$").value(hasKey("idPerfil")));
            verify(usuarioService, times(0)).atualizarPerfil(any(UUID.class), anyInt());
        }
    }

    @Nested
    class AtualizarEnderecoRequest {
        @Test
        void devePermitirAtualizarEndereco() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            doNothing().when(usuarioService)
                    .atualizarDadosEndereco(any(UUID.class), any(EnderecoRecordRequest.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            verify(usuarioService, times(1)).atualizarDadosEndereco(any(UUID.class), any(EnderecoRecordRequest.class));
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            doThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados.")).when(usuarioService).atualizarDadosEndereco(any(UUID.class), any(EnderecoRecordRequest.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.*").value(hasItem("Usuário não encontrado na base de dados.")));
            verify(usuarioService, times(1)).atualizarDadosEndereco(any(UUID.class), any(EnderecoRecordRequest.class));
        }

        @ParameterizedTest
        @CsvSource({
                " , 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, false, O campo cidade precisa ser informado.",
                "São Gonçalo, , Nova Cidade, Rua Aquidabã, Rio de Janeiro, false, O campo cep precisa ser informado.",
                "São Gonçalo, 24455450, , Rua Aquidabã, Rio de Janeiro, false, O campo bairro precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, , Rio de Janeiro, false, O campo endereco precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, , false, O campo estado precisa ser informado.",
                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, , O campo semNumero precisa ser informado.",
        })
        void deveLancarExcecaoParaCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, Boolean semNumero, String expectedError) throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EnderecoRecordRequest enderecoRecordRequest = new EnderecoRecordRequest(
                    cidade,
                    cep,
                    bairro,
                    endereco,
                    estado,
                    79,
                    "Casa 8",
                    semNumero
            );

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioService, times(0)).cadastrar(any(UsuarioRecordRequest.class));
        }

    }

    @Nested
    class AtualizarNomeRequest {
        @Test
        void devePermitirAtualizarNome() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest("John Doe");

            doNothing().when(usuarioService).atualizarNome(any(UUID.class), any(String.class));

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/nome", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(nomeRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            verify(usuarioService, times(1)).atualizarNome(any(UUID.class), anyString());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest("John Doe");

            doThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados.")).when(usuarioService).atualizarNome(any(UUID.class), anyString());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/nome", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(nomeRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.*").value(hasItem("Usuário não encontrado na base de dados.")));
            verify(usuarioService, times(1)).atualizarNome(any(UUID.class), anyString());
        }

        @ParameterizedTest
        @CsvSource({
                " , O campo nome precisa estar preenchido.",
                "Jo, O campo nome precisa ter entre 3 e 150 caracteres."
        })
        void deveLancarExcecaoParaCamposInvalidosNoDto(String nome, String expectedError) throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest(nome);

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/nome", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(nomeRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
            verify(usuarioService, times(0)).atualizarNome(any(UUID.class), anyString());
        }

        @Test
        void deveLancarExcecaoSeNomeNovoMaiorQue150Characters() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            String nome = "A".repeat(151);
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest(nome);

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/nome", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(nomeRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem("O campo nome precisa ter entre 3 e 150 caracteres.")));
            verify(usuarioService, times(0)).atualizarNome(any(UUID.class), anyString());
        }
    }

    @Nested
    class AtualizarEmailRequest {
        @Test
        void devePermitirAtualizarEmail() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");

            doNothing().when(usuarioService).atualizarEmail(any(UUID.class), anyString());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/email", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(emailRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");

            doThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados.")).when(usuarioService).atualizarEmail(any(UUID.class), anyString());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/email", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(emailRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.*").value(hasItem("Usuário não encontrado na base de dados.")));
            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
        }

        @Test
        void deveLancarExecaoSeEmailJaCadastrado() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");

            doThrow(new EmailDuplicadoException("Já existe um usuário com o email informado.")).when(usuarioService).atualizarEmail(any(UUID.class), anyString());

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/email", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(emailRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.*").value(hasItem("Já existe um usuário com o email informado.")));
            verify(usuarioService, times(1)).atualizarEmail(any(UUID.class), anyString());
        }

        @Test
        void deveLancarExcecaoParaCamposInvalidosNoDto() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("johndoe");

            // Act & Assert
            mockMvc.perform(patch("/usuarios/{id}/email", id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(emailRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$").value(hasKey("email")))
                    .andExpect(jsonPath("$.*").value(hasItem("O e-mail precisa ser válido")));
            verify(usuarioService, times(0)).atualizarEmail(any(UUID.class), anyString());
        }
    }

}
