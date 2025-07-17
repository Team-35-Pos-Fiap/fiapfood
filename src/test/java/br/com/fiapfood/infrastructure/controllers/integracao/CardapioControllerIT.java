package br.com.fiapfood.infrastructure.controllers.integracao;

import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.LoginDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CardapioControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    @DisplayName("Buscar cardapio por id com sucesso")
    @Test
    void deveRetornarCardapioPorId() {
        // Arrange
        UUID id = UUID.fromString("2d2e0bad-347d-40c6-824f-b7d7bbc65449");
        CardapioDto cardapio = new CardapioDto(id,
                "Feijoada", "Feijoada completa com acompanhamentos", 39.90,
                Boolean.TRUE, "/var/lib/docker/volumes/images/_datafeijoada.jpg");

        // Act & Assert
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cardapio)
                .when()
                .get("/cardapios/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
