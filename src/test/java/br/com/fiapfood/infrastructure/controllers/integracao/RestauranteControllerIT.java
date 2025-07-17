package br.com.fiapfood.infrastructure.controllers.integracao;

import br.com.fiapfood.core.entities.dto.*;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

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

    @DisplayName("Deve buscar restaurante por id")
    @Test
    void deveBuscarRestaurantePorId() {
        // Arrange
        UUID id = UUID.fromString("40d5955e-c0bd-41da-b434-e46fa69bda14");
        RestauranteDto restaurante = new RestauranteDto(id, "Restaurante Sabor Brasil",
                new EnderecoDto(UUID.randomUUID(), "", "","","",
                        "",1,""),
                "Brasileira", LocalDateTime.now(),
                new UsuarioDto(UUID.randomUUID(), "",
                        new PerfilDto(1, "admin"),
                        new LoginDto(UUID.randomUUID(), "", ""),
                        Boolean.TRUE,
                        "",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        new EnderecoDto(UUID.randomUUID(), "", "","","",
                                "",1,"")));

        // Act & Assert
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurante)
                .when()
                .get("/restaurantes/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
