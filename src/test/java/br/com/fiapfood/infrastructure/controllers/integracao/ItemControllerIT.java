package br.com.fiapfood.infrastructure.controllers.integracao;


import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosRestauranteResumidoDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ItemControllerIT {

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
        ItemDto item = new ItemDto(id,
                "Feijoada",
                "Feijoada completa com acompanhamentos",
                BigDecimal.valueOf(39.9),
                Boolean.TRUE,
                Boolean.TRUE,
                "/var/lib/docker/volumes/images/_datafeijoada.jpg",
                new DadosRestauranteResumidoDto(UUID.randomUUID(), "Restaurante Sabor Brasil", Boolean.TRUE));

        // Act & Assert
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(item)
                .when()
                .get("/itens/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
