package br.com.fiapfood.integracao.controllers;

import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.request.SenhaRecordRequest;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LoginControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class ValidarLoginRequest {

        @Test
        void devePermitirValidarLogin() {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0001", "123");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(loginRecordRequest)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Acesso liberado"));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoLoginNaoEncontradoAtravesDaMatriculaESenha() {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0001", "1234");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(loginRecordRequest)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Não foi encontrado um usuário com a matrícula e senha informados."));
        }

        @ParameterizedTest
        @CsvSource({
                " , 123, matricula",
                "US, 123, matricula",
                "US00001, 123, matricula",
                "us0001, , senha"
        })
        void deveRetornarStatusBadRequestParaCamposInvalidos(String matricula, String senha, String itemComErro) {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest(matricula, senha);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(loginRecordRequest)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey(itemComErro));
        }

        @Test
        void deveRetornarStatusUnauthorizedQuandoNaoEncontrarUsuarioAtivoVinculadoComOLogin() {
            // Arrange
            LoginRecordRequest loginRecordRequest = new LoginRecordRequest("us0003", "123");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(loginRecordRequest)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("O usuário não possui permissão de acesso."));
        }
    }

    @Nested
    class ValidarAtulizarSenhaRequest {
        @Test
        void devePermitirTrocarSenha() {
            // Arrange
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(senhaRecordRequest)
                    .when()
                    .patch("/login/{matricula}/senha", matricula)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Senha alterada com sucesso."));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoLoginNaoEncontradoAtravesDaMatricula() {
            // Arrange
            String matricula = "us0005";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(senhaRecordRequest)
                    .when()
                    .patch("/login/{matricula}/senha", matricula)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Não foi encontrado um usuário com a matrícula e senha informados."));
        }

        @Test
        void deveRetornarStatusNotFoundQuandoNaoEncontrarUsuarioAtivoVinculadoComOLogin() {
            // Arrange
            String matricula = "us0003";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("124");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(senhaRecordRequest)
                    .when()
                    .patch("/login/{matricula}/senha", matricula)
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Usuário não encontrado na base de dados."));
        }

        @Test
        void deveRetonarStatusBadRequestParaCamposInvalidos() {
            // Arrange
            String matricula = "us0001";
            SenhaRecordRequest senhaRecordRequest = new SenhaRecordRequest("");

            // Act & Arrange
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(senhaRecordRequest)
                    .when()
                    .patch("/login/{matricula}/senha", matricula)
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("senha"))
                    .body("senha", equalTo("O campo senha precisa estar preenchido."));

        }
    }
}
