package br.com.fiapfood.integracao.controllers;

import br.com.fiapfood.entities.record.request.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validEnderecoRecordRequest;
import static br.com.fiapfood.utils.DataGenerator.validLoginRecordRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsuarioControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class CadastrarUsuarioRequest {
        @Test
        void devePermitirCadstrarUsuario() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "John Doe",
                    "johndoe@email.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }


        @Test
        void deveRetornarStatusBadRequestQuandoEmailJaCadastrado() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "John Doe",
                    "thiago@fiapfood.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Já existe um usuário com o email informado."));
        }


        @ParameterizedTest
        @CsvSource({
                " , johndoe@email.com, 1, O campo nome precisa estar preenchido.",
                "Jo, johndoe@email.com, 1, O campo nome precisa ter entre 3 e 150 caracteres.",
                "John Doe, , 1, O campo email precisa estar preenchido.",
                "John Doe, johndoeemail.com, 1,  O e-mail precisa ser válido",
                "John Doe, johndoe@email.com, ,  É necessário informar o perfil de acesso para o usuário."
        })
        void deveRetornarStatusBadRequestQuandoCamposDeUsuarioInvalidos(String nome, String email, Integer perfil, String expectedError) throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(nome, email, perfil, validEnderecoRecordRequest(), validLoginRecordRequest());

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasValue(expectedError));
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
        void deveRetornarStatusBadRequestQuandoCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, Boolean semNumero, String expectedError) throws Exception {
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
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest("John Doe", "johndoe@rmail.com", 1, enderecoRecordRequest, validLoginRecordRequest());

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasValue(expectedError));
        }

        @ParameterizedTest
        @CsvSource({
                " , 123, O campo matrícula não foi informado.",
                "US, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
                "US00001, 123, O campo matrícula precisa ter entre 3 e 6 caracteres.",
                "us0010, , O campo senha não foi informado."
        })
        void deveRetornarStatusBadRequestQuandoCamposDeLoginInvalidos(String matricula, String senha, String expectedError) throws Exception {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest(
                    matricula,
                    senha
            );
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest("John Doe", "johndoe@rmail.com", 1, validEnderecoRecordRequest(), loginRecordRequest);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasValue(expectedError));
        }

        @Test
        void deveRetornarStatusBadRequestQuandoNomeComMaisDe150Caracteres() throws Exception {
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
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("nome"))
                    .body("$", hasValue("O campo nome precisa ter entre 3 e 150 caracteres."));
        }

        @Test
        void deveRetornarStatusNofFoundQuandoPerfilNaoExistente() throws Exception {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = new UsuarioRecordRequest(
                    "John Doe",
                    "johndoe@email.com",
                    3, // Perfil nao cadastrado no banco
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(usuarioRecordRequest)
                    .when()
                    .post("/usuarios")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Perfil não encontrado na base de dados."));
        }
    }

    @Nested
    class InativarUsuarioRequest {
        @Test
        void devePermitirInativarUsuario() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/usuarios/{id}/status", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário inativado com sucesso."));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("fc05db14-7993-4564-bff9-c258b5c738c7");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .delete("/usuarios/{id}/status", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));

            // OBS: Removi a checagem do texto porque estava tendo problemas com encoding
        }
    }

    @Nested
    class ReativarUsuarioRequest {
        @Test
        void devePermitirReativarUsuario() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .patch("/usuarios/{id}/status", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário reativado com sucesso."));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoNaoEncontrarUsuarioInativoPorId() {
            // Arrange
            UUID id = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .patch("/usuarios/{id}/status", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));
        }
    }

    @Nested
    class BuscarUsuarioPorIdRequest {
        @Test
        void devePermitirBuscarUsuarioPorId() {
            // Arrange
            UUID id = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/usuarios/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("id"))
                    .body("$", hasKey("nome"))
                    .body("$", hasKey("email"))
                    .body("$", hasKey("matricula"))
                    .body("$", hasKey("ativo"))
                    .body("$", hasKey("dadosEndereco"))
                    .body("$", hasKey("perfilAcesso"));
        }

        @Test
        void deveRetornarStatusNotFoundeQuandoNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID id = UUID.fromString("4b8bc2dc-fd87-462d-a8a6-6e74674d0383");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/usuarios/{id}", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));
        }
    }

    @Nested
    class BuscarTodosUsuariosRequest {
        @Test
        void deveRetornarListaDeUsuariosPaginada() {
            // Arrange

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/usuarios")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("usuarios"))
                    .body("$", hasKey("dadosPaginacao"))
                    .body("usuarios", hasSize(4));
        }

        @Test
        void deveRetornarStatusBadRequestQuandoPaginaMenorOuIgualAZero() {
            // Arrange
            int pagina = 0;

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/usuarios?pagina={pagina}", pagina)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("O número da página deve ser maior ou igual a 1."));
        }

        @Test
        void deveRetonrarStatusBadRequestQuandoPaginaForMaiorDoQueOLimite() {
            // Arrange
            int pagina = 10;

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/usuarios?pagina={pagina}", pagina)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Não foram encontrados usuários na base de dados."));
        }
    }

    @Nested
    class AtualizarPerfilRequest {
        @Test
        void devePermitirAtualizarPerfil() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(2);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(perfilRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/perfil", id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void deveRetornarStatusBadReuqestQuandoNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(2);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(perfilRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/perfil", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoNaoEcontrarPerfilPorId() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(3);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(perfilRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/perfil", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Perfil não encontrado na base de dados."));
        }

        @Test
        void deveRetornarStatusBadRequestQuandoCamposInvalidosNoDto() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            PerfilRecordRequest perfilRecordRequest = new PerfilRecordRequest(null);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(perfilRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/perfil", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("idPerfil"))
                    .body("idPerfil", equalTo("É necessário informar o perfil de acesso para o usuário."));
        }
    }

    @Nested
    class AtualizarEnderecoRequest {
        @Test
        void devePermitirAtualizarEndereco() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(enderecoRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/endereco", id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        }

        @Test
        void deveRetornarStatusNotFoundNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID id = UUID.fromString("fc05db14-7993-4564-bff9-c258b5c738c7");
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(enderecoRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/endereco", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));

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
        void deveRetornarStatusBadRequestQuandoCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, Boolean semNumero, String expectedError) {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
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
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(enderecoRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/endereco", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasValue(expectedError));
        }
    }

    @Nested
    class AtualizarNomeRequest {
        @Test
        void devePermitirAtualizarNome() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest("John Doe");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(nomeRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/nome", id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void deveRetornarStatusBadRequestQuandoNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest("John Doe");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(nomeRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/nome", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));
        }

        @ParameterizedTest
        @CsvSource({
                " , O campo nome precisa estar preenchido.",
                "Jo, O campo nome precisa ter entre 3 e 150 caracteres."
        })
        void deveRetornarStatusBadRequestQuandoCamposInvalidosNoDto(String nome, String expectedError) {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest(nome);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(nomeRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/nome", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasValue(expectedError));
        }

        @Test
        void deveRetornarStatusBadRequestQuandoNomeNovoMaiorQue150Characters() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            String nome = "A".repeat(151);
            NomeRecordRequest nomeRecordRequest = new NomeRecordRequest(nome);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(nomeRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/nome", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("nome"))
                    .body("nome", equalTo("O campo nome precisa ter entre 3 e 150 caracteres."));
        }
    }

    @Nested
    class AtualizarEmailRequest {
        @Test
        void devePermitirAtualizarEmail() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(emailRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/email", id)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doe@email.com");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(emailRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/email", id)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));;
        }

        @Test
        void deveRetornarStatusBadRequestQuandoEmailJaCadastrado() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("carla.rodrigues@fiapfood.com");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(emailRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/email", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Já existe um usuário com o email informado."));
        }

        @Test
        void deveRetornarStatusBadRequestQuandoCamposInvalidosNoDto() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            EmailRecordRequest emailRecordRequest = new EmailRecordRequest("john.doeemail.com");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(emailRecordRequest)
                    .when()
                    .patch("/usuarios/{id}/email", id)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("email"))
                    .body("email", equalTo("O e-mail precisa ser válido"));
        }
    }
}
