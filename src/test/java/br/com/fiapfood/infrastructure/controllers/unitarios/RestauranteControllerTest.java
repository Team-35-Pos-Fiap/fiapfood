package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.ICardapioCoreController;
import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.infraestructure.controllers.CardapioController;
import br.com.fiapfood.infraestructure.controllers.RestauranteController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IRestauranteCoreController restauranteCoreController;

    @Mock
    UsuarioDto usuarioDto;

    @Mock
    EnderecoDto enderecoDto;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        RestauranteController restauranteController = new RestauranteController(restauranteCoreController);

        this.mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
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

    @Test
    @DisplayName("Deve buscar restaurantes com paginação")
    void deveBuscarRestaurantes() throws Exception {
        // Arrange
        List<RestauranteDto> restauranteDtos = List.of(
                new RestauranteDto(UUID.randomUUID(),
                        "Restaurante Teste",
                        enderecoDto,
                        "vegetariano",
                        LocalDateTime.now(),
                        usuarioDto),
                new RestauranteDto(UUID.randomUUID(),
                        "Bom demais",
                        enderecoDto,
                        "mineira",
                        LocalDateTime.now(),
                        usuarioDto)

        );

        PaginacaoDto paginacao = new PaginacaoDto(1,1,2);

        DadosRestauranteComPaginacaoDto dadosRestaurante = new DadosRestauranteComPaginacaoDto(restauranteDtos, paginacao);
        when(restauranteCoreController.buscarTodos(anyInt())).thenReturn(dadosRestaurante);

        mockMvc.perform(get("/restaurantes?page=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantes.length()").value(2));
    }

    @Test
    @DisplayName("Deve buscar restaurante por id")
    void deveBuscarRestaurantePorId() throws Exception {
        UUID id = UUID.randomUUID();
        RestauranteDto restauranteDto = new RestauranteDto(id,
                "Restaurante Teste",
                enderecoDto,
                "vegetariano",
                LocalDateTime.now(),
                usuarioDto);
        when(restauranteCoreController.buscarPorId(eq(id))).thenReturn(restauranteDto);

        mockMvc.perform(get("/restaurantes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante Teste"))
                .andExpect(jsonPath("$.tipoCozinha").value("vegetariano"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("Deve cadastrar restaurante")
//    void deveCadastrarRestaurante() throws Exception {
//        String restauranteJson = "{\n" +
//                "    \"nome\": \"Restaurante novo\",\n" +
//                "    \"endereco\": {\n" +
//                "        \"cidade\": \"São Paulo\",\n" +
//                "        \"cep\": \"05422002\",\n" +
//                "        \"bairro\": \"Pinheiros\",\n" +
//                "        \"endereco\": \"Rua dos Pinheiros\",\n" +
//                "        \"estado\": \"SP\",\n" +
//                "        \"numero\": 1340,\n" +
//                "        \"complemento\": \"Conjunto 25\",\n" +
//                "        \"semNumero\": false\n" +
//                "    },\n" +
//                "    \"tipoCozinha\": \"exótica\",\n" +
//                "    \"horarioFuncionamento\": \"2024-06-01T18:00:00\",\n" +
//                "    \"donoRestaurante\": {\n" +
//                "        \"nome\" : \"Thiago Motta\",\n" +
//                "        \"email\" : \"thiago.mottaa@fiapfood.com\",\n" +
//                "        \"dadosEndereco\" : {\n" +
//                "            \"cidade\" : \"Nome da cidade\",\n" +
//                "            \"cep\" : \"24455077\",\n" +
//                "            \"bairro\" : \"Nome do bairro\",\n" +
//                "            \"endereco\" : \"Descrição do endereço\",\n" +
//                "            \"estado\" : \"Rio de Janeiro\",\n" +
//                "            \"numero\": 7,\n" +
//                "            \"complemento\" : \"Complemento do endereço\",\n" +
//                "            \"semNumero\" : false\n" +
//                "        },\n" +
//                "        \"perfil\": 1,\n" +
//                "        \"dadosLogin\" : {\n" +
//                "            \"matricula\" : \"us019\",\n" +
//                "            \"senha\" : \"123\"\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//
//        doNothing().when(restauranteCoreController).cadastrar(any(DadosRestauranteDto.class));
//
//        mockMvc.perform(post("/restaurantes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(restauranteJson))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    @DisplayName("Deve atualizar restaurante")
//    void deveAtualizarRestaurante() throws Exception {
//        UUID id = UUID.randomUUID();
//        String restauranteJson = "{ \"nome\": \"Restaurante Atualizado\", \"descricao\": \"Desc\", \"cnpj\": \"12345678901234\", \"telefone\": \"11999999999\", \"email\": \"teste@exemplo.com\" }";
//        doNothing().when(restauranteCoreController).atualizarRestaurante(eq(id), any(DadosRestauranteDto.class));
//
//        mockMvc.perform(patch("/restaurantes/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(restauranteJson))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Deve atualizar endereço do restaurante")
//    void deveAtualizarEnderecoRestaurante() throws Exception {
//        UUID id = UUID.randomUUID();
//        String enderecoJson = "{ \"logradouro\": \"Rua X\", \"numero\": \"123\", \"bairro\": \"Centro\", \"cidade\": \"SP\", \"estado\": \"SP\", \"cep\": \"01234567\" }";
//        doNothing().when(restauranteCoreController).atualizarEnderecoRestaurante(eq(id), any(DadosEnderecoDto.class));
//
//        mockMvc.perform(patch("/restaurantes/{id}/endereco", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(enderecoJson))
//                .andExpect(status().isNoContent());
//    }

    @Test
    @DisplayName("Deve deletar restaurante")
    void deveDeletarRestaurante() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(restauranteCoreController).deletarRestaurante(eq(id));

        mockMvc.perform(delete("/restaurantes/{id}", id))
                .andExpect(status().isNoContent());
    }
}
