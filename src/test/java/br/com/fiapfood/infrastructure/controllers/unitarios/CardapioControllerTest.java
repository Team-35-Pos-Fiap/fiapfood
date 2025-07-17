package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.ICardapioCoreController;
import br.com.fiapfood.core.entities.dto.*;
import br.com.fiapfood.core.exceptions.CardapioNaoEncontradoException;
import br.com.fiapfood.infraestructure.controllers.CardapioController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CardapioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICardapioCoreController cardapioCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        CardapioController cardapioController = new CardapioController(cardapioCoreController);

        this.mockMvc = MockMvcBuilders.standaloneSetup(cardapioController)
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

        @DisplayName("Buscar todos os cardapios cadastrados")
        @Test
        void deveRetornarListaComCardapiosCadastrados() throws Exception {
            // Arrange
            List<CardapioDto> cardapios = List.of(
                    new CardapioDto(UUID.randomUUID(), "Feijoada", 
                            "Feijoada completa com acompanhamentos", 39.90, 
                            Boolean.TRUE, "/var/lib/docker/volumes/images/_datafeijoada.jpg"),
                    new CardapioDto(UUID.randomUUID(), "Lasanha", 
                            "Lasanha de carne com queijo", 29.50, 
                            Boolean.TRUE, "/var/lib/docker/volumes/images/_datalasanha.jpg")
                    
            );
            
            PaginacaoDto paginacao = new PaginacaoDto(1,1,2);

            DadosCardapioComPaginacaoDto dadosCardapio = new DadosCardapioComPaginacaoDto(cardapios, paginacao);

            when(cardapioCoreController.buscarTodos(1)).thenReturn(dadosCardapio);

            // Act & Assert
            mockMvc.perform(get("/cardapios")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.cardapios.length()").value(2))
                    .andExpect(jsonPath("$.cardapios[0].nome").value("Feijoada"))
                    .andExpect(jsonPath("$.cardapios[1].nome").value("Lasanha"))
                    .andExpect(jsonPath("$.dadosPaginacao.paginaAtual").value(1))
                    .andExpect(jsonPath("$.dadosPaginacao.totalPaginas").value(1))
                    .andExpect(jsonPath("$.dadosPaginacao.totalItens").value(2));

            verify(cardapioCoreController, times(1)).buscarTodos(anyInt());
        }

        @DisplayName("Buscar cardapio por id com sucesso")
        @Test
        void deveRetornarCardapioPorId() throws Exception {
            // Arrange
            CardapioDto cardapio = new CardapioDto(UUID.randomUUID(), "Feijoada",
                            "Feijoada completa com acompanhamentos", 39.90,
                            Boolean.TRUE, "/var/lib/docker/volumes/images/_datafeijoada.jpg");

            when(cardapioCoreController.buscarPorId(any(UUID.class))).thenReturn(cardapio);

            // Act & Assert
            mockMvc.perform(get("/cardapios/{id}", UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$.nome").value("Feijoada"))
                    .andExpect(status().isOk());

            verify(cardapioCoreController, times(1)).buscarPorId(any(UUID.class));
        }

        @DisplayName("Buscar cardapio por id com erro. Cardapio nao encontrado através do id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarCardapioPorId() throws Exception {
            // Arrange
            UUID cardapioId = UUID.randomUUID();

            when(cardapioCoreController.buscarPorId(any(UUID.class)))
                    .thenThrow(new CardapioNaoEncontradoException("Não foi encontrado nenhum cardapio com o id informado."));

            // Act & Assert
            mockMvc.perform(get("/cardapios/{id}", cardapioId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum cardapio com o id informado."));

            verify(cardapioCoreController, times(1)).buscarPorId(any(UUID.class));
        }

    @DisplayName("Deve cadastrar um novo cardápio com sucesso")
    @Test
    void deveCadastrarCardapio() throws Exception {
        // Arrange
        String novoCardapioJson = "{\n" +
                "  \"nome\": \"Prato Exemplo\",\n" +
                "  \"descricao\": \"Descrição do prato\",\n" +
                "  \"preco\": 29.90,\n" +
                "  \"disponivelApenasRestaurante\": true,\n" +
                "  \"fotoPrato\": \"imagem_prato.jpg\"\n" +
                "}";

        // Simula o comportamento do controller core
        doNothing().when(cardapioCoreController).cadastrar(any(DadosCardapioDto.class));

        // Act & Assert
        mockMvc.perform(
                        post("/cardapios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(novoCardapioJson)
                )
                .andDo(print())
                .andExpect(status().isCreated());

        verify(cardapioCoreController, times(1)).cadastrar(any(DadosCardapioDto.class));
    }

    @Test
    @DisplayName("Deve atualizar um cardápio com sucesso")
    void deveAtualizarCardapio() throws Exception {
        // Arrange
        UUID cardapioId = UUID.randomUUID();
        String atualizarCardapioJson = "{\n" +
                "  \"nome\": \"Prato Atualizado\",\n" +
                "  \"descricao\": \"Descrição atualizada\",\n" +
                "  \"preco\": 35.90,\n" +
                "  \"disponivelApenasRestaurante\": false,\n" +
                "  \"fotoPrato\": \"imagem_atualizada.jpg\"\n" +
                "}";

        doNothing().when(cardapioCoreController).atualizar(eq(cardapioId), any(DadosCardapioDto.class));

        // Act & Assert
        mockMvc.perform(
                        patch("/cardapios/{id}", cardapioId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(atualizarCardapioJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(cardapioCoreController, times(1)).atualizar(eq(cardapioId), any(DadosCardapioDto.class));
    }

    @Test
    @DisplayName("Deve deletar um cardápio com sucesso")
    void deveDeletarCardapio() throws Exception {
        // Arrange
        UUID cardapioId = UUID.randomUUID();
        doNothing().when(cardapioCoreController).deletarCardapio(eq(cardapioId));

        // Act & Assert
        mockMvc.perform(
                        delete("/cardapios/{id}", cardapioId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(cardapioCoreController, times(1)).deletarCardapio(eq(cardapioId));
    }
}
