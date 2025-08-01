package br.com.fiapfood.infrastructure.controllers.integracao;


import br.com.fiapfood.infraestructure.controllers.request.atendimento.AtendimentoDto;
import br.com.fiapfood.infraestructure.controllers.request.login.LoginDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.CadastrarRestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosTipoCulinariaDto;
import br.com.fiapfood.infraestructure.controllers.request.tipo_culinaria.TipoCulinariaDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.CadastrarUsuarioDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static br.com.fiapfood.utils.DtoDataGenerator.*;
import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
    class GerenciarRestauranteRequest {

        @Test
        @DisplayName("Deve buscar restaurantes com paginação")
        void deveBuscarRestaurantesComSucesso() throws Exception {
            // Arrange

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/restaurantes")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("restaurantes"))
                    .body("$", hasKey("paginacao"))
                    .body("restaurantes", hasSize(5));
        }

        @Test
        @DisplayName("Deve buscar restaurante por id")
        void deveBuscarRestaurantePorIdComSucesso() throws Exception {
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

        @Test
        @DisplayName("Deve cadastrar restaurante com sucesso")
        void deveCadastrarRestauranteComSucesso() throws Exception {
            // Arrange

            AtendimentoDto atendimento1 = new AtendimentoDto(
                    null,
                    "SEGUNDA-FEIRA",
                    LocalTime.of(9, 0, 0),
                    LocalTime.of(16, 0, 0)
            );

            AtendimentoDto atendimento2 = new AtendimentoDto(
                    null,
                    "TERÇA-FEIRA",
                    LocalTime.of(9, 0, 0),
                    LocalTime.of(16, 0, 0)
            );

            List<AtendimentoDto> atendimentos = List.of(atendimento1, atendimento2);

            CadastrarRestauranteDto dadosRestaurante = new CadastrarRestauranteDto(
                    "Restaurante novo",
                    dadosEnderecoDtoValido(),
                    UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c"),
                    1,
                    atendimentos);

            //Act & Assert
            given()
                    .contentType(ContentType.JSON)
                    .body(dadosRestaurante)
                    .when()
                    .post("/restaurantes")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());


        }

        @Test
        @DisplayName("Deve inativar restaurante")
        void deveInativarRestauranteComSucesso() throws Exception {
            // Arrange
            UUID idRestauranteAtivo = UUID.fromString("a72181a6-7699-4686-a5ec-1a0431764e62");

            // Act & Assert - Inativa restaurante
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .patch("/restaurantes/{id}/status/inativa", idRestauranteAtivo)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("mensagem", equalTo("Restaurante inativado com sucesso."));

            // Act & Assert - Valida inativação do restaurante
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/restaurantes/{id}", idRestauranteAtivo)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(idRestauranteAtivo.toString()))
                    .body("isAtivo", equalTo(false));

        }

        @Test
        @DisplayName("Deve reativar restaurante")
        void deveReativarRestauranteComSucesso() throws Exception {
            // Arrange
            UUID idRestauranteInativo = UUID.fromString("fc8a9535-d6be-465f-8bf1-d9885e91c91d");

            // Act & Assert - reativa restaurante
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .patch("/restaurantes/{id}/status/reativa", idRestauranteInativo)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("mensagem", equalTo("Restaurante reativado com sucesso."));

            // Act & Assert - Valida reativação do restaurante
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/restaurantes/{id}", idRestauranteInativo)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(idRestauranteInativo.toString()))
                    .body("isAtivo", equalTo(true));
        }

        @Test
        @DisplayName("Deve atualizar TipoCulinaria restaurante")
        void deveAtualizarTipoCulinariaRestauranteComSucesso() throws Exception {

            // Arrange
            UUID idRestaurante = UUID.fromString("a72181a6-7699-4686-a5ec-1a0431764e62");
            DadosTipoCulinariaDto novoTipoCulinaria = new DadosTipoCulinariaDto(2);

            // Act & Assert
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(novoTipoCulinaria)
                    .when()
                    .patch("/restaurantes/{id}/tipo-culinaria", idRestaurante)
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());

            // Valida atualização do tipo de culinária
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get("/restaurantes/{id}", idRestaurante)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(idRestaurante.toString()))
                    .body("tipoCulinaria.id", equalTo(2));
        }

        @Test
        @DisplayName("Deve atualizar dono do restaurante")
        void deveAtualizarDonoRestauranteComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar nome do restaurante")
        void deveAtualizarNomeRestauranteComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar endereco do restaurante")
        void deveAtualizarEnderecoRestauranteComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar atendimento do restaurante")
        void deveAtualizarAtendimentoRestauranteComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve adicionar Atendimento do restaurante")
        void deveAdicionarAtendimentoRestauranteComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve excluir Atendimento do restaurante")
        void deveExcluirAtendimentoRestauranteComSucesso() throws Exception {

        }

    }

    @Nested
    class GerenciarItemRequest {

        @DisplayName("Buscar todos os itens cadastrados")
        @Test
        void deveRetornarListaComItensCadastradosComSucesso() throws Exception {

        }

        @DisplayName("Buscar item por id com sucesso")
        @Test
        void deveRetornarItemPorIdComSucesso() throws Exception {

        }

        @DisplayName("Buscar item por id com erro. Item nao encontrado através do id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarItemPorId() throws Exception {

        }

        @DisplayName("Deve cadastrar um novo cardápio com sucesso")
        @Test
        void deveCadastrarItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar a descricao do item com sucesso")
        void deveAtualizarDescricaoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o nome do item com sucesso")
        void deveAtualizarNomeItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o preco do item com sucesso")
        void deveAtualizarPrecoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o disponibilidadeConsumoPresencial do item com sucesso")
        void deveAtualizarDisponibilidadeConsumoPresencialItemComSucesso() throws Exception {

        }


        @Test
        @DisplayName("Deve atualizar o disponibilidade do item com sucesso")
        void deveAtualizarDisponibilidadeComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar a imagem do item com sucesso")
        void deveAtualizarImagemDoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve baixar a imagem do item com sucesso")
        void deveBaixarImagemDoItemComSucesso() throws Exception {

        }

    }

}
