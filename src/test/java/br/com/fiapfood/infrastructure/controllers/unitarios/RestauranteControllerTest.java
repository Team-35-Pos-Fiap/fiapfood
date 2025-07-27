package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
import br.com.fiapfood.core.exceptions.item.ItemNaoEncontradoException;
import br.com.fiapfood.infraestructure.controllers.RestauranteController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.infraestructure.controllers.request.endereco.EnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.item.DescricaoDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemDto;
import br.com.fiapfood.infraestructure.controllers.request.item.ItemPaginacaoDto;
import br.com.fiapfood.infraestructure.controllers.request.paginacao.PaginacaoDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioDto;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IRestauranteCoreController restauranteCoreController;

    @Mock
    ItemDto itemDto;

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

//    @Test
//    @DisplayName("Deve buscar restaurantes com paginação")
//    void deveBuscarRestaurantes() throws Exception {
//        // Arrange
//        List<RestauranteDto> restauranteDtos = List.of(
//                new RestauranteDto(UUID.randomUUID(),
//                        "Restaurante Teste",
//                        enderecoDto,
//                        "vegetariano",
//                        LocalDateTime.now(),
//                        usuarioDto),
//                new RestauranteDto(UUID.randomUUID(),
//                        "Bom demais",
//                        enderecoDto,
//                        "mineira",
//                        LocalDateTime.now(),
//                        usuarioDto)
//
//        );
//
//        PaginacaoDto paginacao = new PaginacaoDto(1,1,2);
//
//        DadosRestauranteComPaginacaoDto dadosRestaurante = new DadosRestauranteComPaginacaoDto(restauranteDtos, paginacao);
//        when(restauranteCoreController.buscarTodos(anyInt())).thenReturn(dadosRestaurante);
//
//        mockMvc.perform(get("/restaurantes?page=1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.restaurantes.length()").value(2));
//    }
//
//    @Test
//    @DisplayName("Deve buscar restaurante por id")
//    void deveBuscarRestaurantePorId() throws Exception {
//        UUID id = UUID.randomUUID();
//        RestauranteDto restauranteDto = new RestauranteDto(id,
//                "Restaurante Teste",
//                enderecoDto,
//                "vegetariano",
//                LocalDateTime.now(),
//                usuarioDto);
//        when(restauranteCoreController.buscarPorId(eq(id))).thenReturn(restauranteDto);
//
//        mockMvc.perform(get("/restaurantes/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.nome").value("Restaurante Teste"))
//                .andExpect(jsonPath("$.tipoCozinha").value("vegetariano"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("Deve cadastrar restaurante")
//    void deveCadastrarRestaurante() throws Exception {
//        String restauranteJson = "{\n" +
//                "    \"nome\": \"Restaurante novo\"\n" +
//                "    \", endereco\": " + UUID.randomUUID() +
//                "    \", tipoCozinha\": \"exótica\"\n" +
//                "    \", horarioFuncionamento\": \"2024-06-01T18:00:00\"\n" +
//                "    \", donoRestaurante\": " + UUID.randomUUID() +
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
//
//        String restauranteJson = "{\n" +
//                "    \"nome\": \"Restaurante novo\"\n" +
//                "    \", endereco\": " + UUID.randomUUID() +
//                "    \", tipoCozinha\": \"exótica\"\n" +
//                "    \", horarioFuncionamento\": \"2024-06-01T18:00:00\"\n" +
//                "    \", donoRestaurante\": " + UUID.randomUUID() +
//                "    }\n" +
//                "}";
//
//        doNothing().when(restauranteCoreController).atualizarRestaurante(eq(id), any(DadosRestauranteDto.class));
//
//        mockMvc.perform(patch("/restaurantes/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(restauranteJson))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Deve deletar restaurante")
//    void deveDeletarRestaurante() throws Exception {
//        UUID id = UUID.randomUUID();
//        doNothing().when(restauranteCoreController).deletarRestaurante(eq(id));
//
//        mockMvc.perform(delete("/restaurantes/{id}", id))
//                .andExpect(status().isNoContent());
//    }

    @Nested
    class GerenciarItemRequest {

        @DisplayName("Buscar todos os itens cadastrados")
        @Test
        void deveRetornarListaComItensCadastrados() throws Exception {
            // Arrange

            UUID idRestaurante = UUID.randomUUID();

            List<ItemDto> itens = List.of( itemDto, itemDto );

            when(restauranteCoreController.buscarTodosItens(any(UUID.class))).thenReturn(itens);

            // Act & Assert
            mockMvc.perform(get("/restaurantes/{id-restaurante}/itens", idRestaurante)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2));

            verify(restauranteCoreController, times(1)).buscarTodosItens(any(UUID.class));
        }

        @DisplayName("Buscar item por id com sucesso")
        @Test
        void deveRetornarItemPorId() throws Exception {
            // Arrange
            UUID idRestaurante = UUID.randomUUID();
            UUID idItem = UUID.randomUUID();
            ItemDto item = new ItemDto(idItem,
                    "Feijoada",
                    "Feijoada completa com acompanhamentos",
                    BigDecimal.valueOf(39.9),
                    Boolean.TRUE,
                    Boolean.TRUE,
                    "/var/lib/docker/volumes/images/_datafeijoada.jpg");

            when(restauranteCoreController.buscarItemPorId(any(UUID.class), any(UUID.class))).thenReturn(item);

            // Act & Assert
            mockMvc.perform(get("/restaurantes/{id-restaurante}/itens/{id-item}", idRestaurante, idItem)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nome").value("Feijoada"));

            verify(restauranteCoreController, times(1)).buscarItemPorId(eq(idRestaurante), eq(idItem));
        }

        @DisplayName("Buscar item por id com erro. Item nao encontrado através do id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarItemPorId() throws Exception {
            // Arrange
            UUID idRestaurante = UUID.randomUUID();
            UUID idItem = UUID.randomUUID();

            when(restauranteCoreController.buscarItemPorId(any(UUID.class), any(UUID.class)))
                    .thenThrow(new ItemNaoEncontradoException("Não foi encontrado nenhum item com o id informado."));

            // Act & Assert
            mockMvc.perform(get("/restaurantes/{id-restaurante}/itens/{id-item}", idRestaurante, idItem)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum item com o id informado."));

            verify(restauranteCoreController, times(1)).buscarItemPorId(eq(idRestaurante), eq(idItem));
        }

        @DisplayName("Deve cadastrar um novo cardápio com sucesso")
        @Test
        void deveCadastrarItem() throws Exception {

            UUID idRestaurante = UUID.randomUUID();
            String nome = "Prato Exemplo";
            String descricao = "Descrição do prato";
            String preco = "29.90";
            String disponivel = "true";
            MockMultipartFile imagem = new MockMultipartFile(
                    "imagem",
                    "imagem_prato.jpg",
                    "image/jpeg",
                    "conteudo".getBytes());

            doNothing().when(restauranteCoreController).cadastrar(any(), any(), any(), any(), any(), any());

            mockMvc.perform(multipart("/restaurantes/{id-restaurante}/itens", idRestaurante)
                            .file(imagem)
                            .param("nome", nome)
                            .param("descricao", descricao)
                            .param("preco", preco)
                            .param("disponivelParaConsumoPresencial", disponivel)
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isCreated());

            verify(restauranteCoreController, times(1)).cadastrar(any(), any(), any(), any(), any(), any());
        }

        @Test
        @DisplayName("Deve atualizar a descricao do item com sucesso")
        void deveAtualizarDescricaoItem() throws Exception {

            UUID idRestaurante = UUID.randomUUID();
            UUID idItem = UUID.randomUUID();
            DescricaoDto novaDescricao = new DescricaoDto("Feijoada Especial para duas pessoas");

            // Mock do core controller
            doNothing().when(restauranteCoreController).atualizarDescricaoItem(any(), any(), any());

            mockMvc.perform(patch("/restaurantes/{id-restaurante}/itens/{id-item}/descricao", idRestaurante, idItem)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(novaDescricao)))
                    .andDo(print())
                    .andExpect(status().isNoContent());

//            verify(restauranteCoreController, times(1)).atualizarDescricaoItem(eq(idRestaurante), eq(idItem), any(DescricaoDto.class));
        }

//        @Test
//        @DisplayName("Deve atualizar o nome do item com sucesso")
//        void deveAtualizarNomeItem() throws Exception {
//            UUID id = UUID.randomUUID();
//            String novoNome = "Feijoada Especial";
//
//            // Mock do core controller
//            doNothing().when(itemCoreController).atualizarNome(eq(id), eq(novoNome));
//
//            mockMvc.perform(patch("/itens/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content("{\"nome\":\"" + novoNome + "\"}"))
//                    .andExpect(status().isNoContent());
//
//            verify(itemCoreController, times(1)).atualizarNome(eq(id), eq(novoNome));
//        }
//
//        @Test
//        @DisplayName("Deve atualizar o preco do item com sucesso")
//        void deveAtualizarPrecoItem() throws Exception {
//            UUID id = UUID.randomUUID();
//            BigDecimal novoPreco = BigDecimal.valueOf(33.25);
//
//            // Mock do core controller
//            doNothing().when(itemCoreController).atualizarPreco(eq(id), eq(novoPreco));
//
//            mockMvc.perform(patch("/itens/{id}/preco", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content("{\"preco\":\"" + novoPreco + "\"}"))
//                    .andExpect(status().isNoContent());
//
//            verify(itemCoreController, times(1)).atualizarPreco(eq(id), eq(novoPreco));
//        }
//
//        @Test
//        @DisplayName("Deve atualizar o disponibilidadeConsumoPresencial do item com sucesso")
//        void deveAtualizarDisponibilidadeConsumoPresencialItem() throws Exception {
//            UUID id = UUID.randomUUID();
//            Boolean disponibilidadeConsumoPresencial = false;
//
//            // Mock do core controller
//            doNothing().when(itemCoreController).atualizarDisponibilidadeConsumoPresencial(
//                    eq(id),
//                    eq(disponibilidadeConsumoPresencial)
//            );
//
//            mockMvc.perform(patch("/itens/{id}/disponibilidade-consumo-presencial", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content("{\"isDisponivel\":\"" + disponibilidadeConsumoPresencial + "\"}"))
//                    .andExpect(status().isNoContent());
//
//            verify(itemCoreController, times(1)).atualizarDisponibilidadeConsumoPresencial(
//                    eq(id),
//                    eq(disponibilidadeConsumoPresencial)
//            );
//        }
//
//        @Test
//        @DisplayName("Deve atualizar o disponibilidade do item com sucesso")
//        void deveAtualizarDisponibilidade() throws Exception {
//            UUID id = UUID.randomUUID();
//            Boolean disponibilidade = false;
//
//            // Mock do core controller
//            doNothing().when(itemCoreController).atualizarDisponibilidade( eq(id), eq(disponibilidade));
//
//            mockMvc.perform(patch("/itens/{id}/disponibilidade", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content("{\"isDisponivel\":\"" + disponibilidade + "\"}"))
//                    .andExpect(status().isNoContent());
//
//            verify(itemCoreController, times(1)).atualizarDisponibilidade( eq(id), eq(disponibilidade));
//        }
//
//        @Test
//        @DisplayName("Deve atualizar a imagem do item com sucesso")
//        void deveAtualizarImagemDoItem() throws Exception {
//            UUID id = UUID.randomUUID();
//            MockMultipartFile imagem = new MockMultipartFile("imagem", "imagem.jpg", "image/jpeg", "conteudo".getBytes());
//
//            doNothing().when(itemCoreController).atualizarImagem(eq(id), any(MultipartFile.class));
//
//            mockMvc.perform(multipart("/itens/{id}/imagem", id)
//                            .file(imagem)
//                            .with(request -> { request.setMethod("PATCH"); return request; }))
//                    .andExpect(status().isNoContent());
//
//            verify(itemCoreController, times(1)).atualizarImagem(eq(id), any(MultipartFile.class));
//        }
//
//        @Test
//        @DisplayName("Deve baixar a imagem do item com sucesso")
//        void deveBaixarImagemDoItem() throws Exception {
//            UUID id = UUID.randomUUID();
//            String nomeArquivo = "imagem.jpg";
//            byte[] conteudo = "conteudo da imagem".getBytes();
//            ImagemCoreDto imagemCoreDto = new ImagemCoreDto(id, nomeArquivo, conteudo, "image/jpeg");
//
//            when(itemCoreController.baixarImagem(eq(id))).thenReturn(imagemCoreDto);
//
//            mockMvc.perform(get("/itens/{id}/imagem", id))
//                    .andExpect(status().isOk())
//                    .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\""))
//                    .andExpect(content().bytes(conteudo));
//
//            verify(itemCoreController, times(1)).baixarImagem(eq(id));
//        }

    }
}
