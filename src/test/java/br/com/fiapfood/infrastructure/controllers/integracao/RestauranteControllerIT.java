package br.com.fiapfood.infrastructure.controllers.integracao;


import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosRestauranteResumidoDto;
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

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RestauranteControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class GerenciarItemRequest {
        @DisplayName("Buscar restaurante por id com sucesso")
        @Test
        void deveRetornarRestaurantePorId() {
            // Arrange
            UUID id = UUID.fromString("a72181a6-7699-4686-a5ec-1a0431764e62");

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/restaurantes/{id}", id)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(id.toString()));
        }
    }

}
