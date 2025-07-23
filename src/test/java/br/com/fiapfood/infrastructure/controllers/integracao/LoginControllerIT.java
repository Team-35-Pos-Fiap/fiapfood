package br.com.fiapfood.infrastructure.controllers.integracao;

import br.com.fiapfood.infraestructure.controllers.request.login.LoginDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

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

        @DisplayName("Validação de login com sucesso")
        @Test
        void devePermitirValidarLogin() {
            // Arrange
            LoginDto dadosDeLogin = new LoginDto(UUID.fromString("c303266f-9d32-4dde-8f4c-d8ee13b24ae9"), "us0001", "123");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dadosDeLogin)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("mensagem"))
                    .body("mensagem", equalTo("Acesso liberado."));
        }

    }

}
