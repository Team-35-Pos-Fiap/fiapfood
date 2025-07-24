//package br.com.fiapfood.infrastructure.controllers.unitarios;
//
//
//import br.com.fiapfood.core.controllers.interfaces.IItemCoreController;
//import br.com.fiapfood.core.entities.dto.item.DadosItemDto;
//import br.com.fiapfood.core.entities.dto.item.ImagemCoreDto;
//import br.com.fiapfood.core.exceptions.item.ItemNaoEncontradoException;
//import br.com.fiapfood.infraestructure.controllers.ItemController;
//import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
//import br.com.fiapfood.infraestructure.controllers.request.item.*;
//import br.com.fiapfood.infraestructure.controllers.request.paginacao.PaginacaoDto;
//import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosRestauranteResumidoDto;
//import org.junit.jupiter.api.*;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//public class ItemControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private IItemCoreController itemCoreController;
//
//    @Mock
//    private DadosRestauranteResumidoDto dadosRestauranteResumidoDto;
//
//    AutoCloseable mock;
//
//    @BeforeEach
//    void setUp() {
//        mock = MockitoAnnotations.openMocks(this);
//        ItemController itemController = new ItemController(itemCoreController);
//
//        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController)
//                .setControllerAdvice(new ErrorHandler())
//                .addFilter((request, response, chain) -> {
//                    response.setCharacterEncoding("UTF-8");
//                    chain.doFilter(request, response);
//                }, "/*")
//                .build();
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        mock.close();
//    }
//
//    @DisplayName("Buscar todos os itens cadastrados")
//    @Test
//    void deveRetornarListaComItensCadastrados() throws Exception {
//        // Arrange
//        List<ItemDto> itens = List.of(
//                new ItemDto(UUID.randomUUID(),
//                        "Feijoada",
//                        "Feijoada completa com acompanhamentos",
//                        BigDecimal.valueOf(39.9),
//                        Boolean.TRUE,
//                        Boolean.TRUE,
//                        "/var/lib/docker/volumes/images/_datafeijoada.jpg",
//                        dadosRestauranteResumidoDto),
//                new ItemDto(UUID.randomUUID(),
//                        "Lasanha",
//                        "Lasanha de carne com queijo",
//                        BigDecimal.valueOf(29.50),
//                        Boolean.TRUE,
//                        Boolean.TRUE,
//                        "/var/lib/docker/volumes/images/_datalasanha.jpg",
//                        dadosRestauranteResumidoDto)
//
//        );
//
//        PaginacaoDto paginacao = new PaginacaoDto(1, 1, 2);
//
//        ItemPaginacaoDto itensPaginacao = new ItemPaginacaoDto(itens, paginacao);
//
//        when(itemCoreController.buscarTodos(1)).thenReturn(itensPaginacao);
//
//        // Act & Assert
//        mockMvc.perform(get("/itens")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$.itens[0].nome").value("Feijoada"))
//                .andExpect(jsonPath("$.itens[1].nome").value("Lasanha"))
//                .andExpect(jsonPath("$.paginacao.paginaAtual").value(1))
//                .andExpect(jsonPath("$.paginacao.totalPaginas").value(1))
//                .andExpect(jsonPath("$.paginacao.totalItens").value(2));
//
//        verify(itemCoreController, times(1)).buscarTodos(anyInt());
//    }
//
//    @DisplayName("Buscar item por id com sucesso")
//    @Test
//    void deveRetornarItemPorId() throws Exception {
//        // Arrange
//        UUID itemId = UUID.randomUUID();
//        ItemDto item = new ItemDto(itemId,
//                "Feijoada",
//                "Feijoada completa com acompanhamentos",
//                BigDecimal.valueOf(39.9),
//                Boolean.TRUE,
//                Boolean.TRUE,
//                "/var/lib/docker/volumes/images/_datafeijoada.jpg",
//                dadosRestauranteResumidoDto);
//
//        when(itemCoreController.buscarPorId(eq(itemId))).thenReturn(item);
//
//        // Act & Assert
//        mockMvc.perform(get("/itens/{id}", itemId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$.nome").value("Feijoada"));
//
//        verify(itemCoreController, times(1)).buscarPorId(eq(itemId));
//    }
//
//    @DisplayName("Buscar item por id com erro. Item nao encontrado através do id")
//    @Test
//    void deveLancarExcecaoSeNaoEncontrarItemPorId() throws Exception {
//        // Arrange
//        UUID itemId = UUID.randomUUID();
//
//        when(itemCoreController.buscarPorId(any(UUID.class)))
//                .thenThrow(new ItemNaoEncontradoException("Não foi encontrado nenhum item com o id informado."));
//
//        // Act & Assert
//        mockMvc.perform(get("/itens/{id}", itemId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum item com o id informado."));
//
//        verify(itemCoreController, times(1)).buscarPorId(any(UUID.class));
//    }
//
//    @DisplayName("Deve cadastrar um novo cardápio com sucesso")
//    @Test
//    void deveCadastrarItem() throws Exception {
//        MockMultipartFile imagem = new MockMultipartFile("imagem", "imagem_prato.jpg", "image/jpeg", "conteudo".getBytes());
//        String nome = "Prato Exemplo";
//        String descricao = "Descrição do prato";
//        String preco = "29.90";
//        String disponivel = "true";
//        String idRestaurante = UUID.randomUUID().toString();
//
//        doNothing().when(itemCoreController).cadastrar(
//                eq(nome), eq(descricao), eq(new BigDecimal(preco)), eq(Boolean.valueOf(disponivel)), any(MultipartFile.class), eq(UUID.fromString(idRestaurante))
//        );
//
//        mockMvc.perform(multipart("/itens")
//                        .file(imagem)
//                        .param("nome", nome)
//                        .param("descricao", descricao)
//                        .param("preco", preco)
//                        .param("disponivelParaConsumoPresencial", disponivel)
//                        .param("idRestaurante", idRestaurante)
//                        .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().isCreated());
//
//        verify(itemCoreController, times(1)).cadastrar(any(), any(), any(), any(), any(), any());
//    }
//
//    @Test
//    @DisplayName("Deve atualizar a descricao do item com sucesso")
//    void deveAtualizarDescricaoItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        String novaDescricao = "Feijoada Especial para duas pessoas";
//
//        // Mock do core controller
//        doNothing().when(itemCoreController).atualizarDescricao(eq(id), eq(novaDescricao));
//
//        mockMvc.perform(patch("/itens/{id}/descricao", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"descricao\":\"" + novaDescricao + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarDescricao(eq(id), eq(novaDescricao));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar o nome do item com sucesso")
//    void deveAtualizarNomeItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        String novoNome = "Feijoada Especial";
//
//        // Mock do core controller
//        doNothing().when(itemCoreController).atualizarNome(eq(id), eq(novoNome));
//
//        mockMvc.perform(patch("/itens/{id}/nome", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nome\":\"" + novoNome + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarNome(eq(id), eq(novoNome));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar o preco do item com sucesso")
//    void deveAtualizarPrecoItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        BigDecimal novoPreco = BigDecimal.valueOf(33.25);
//
//        // Mock do core controller
//        doNothing().when(itemCoreController).atualizarPreco(eq(id), eq(novoPreco));
//
//        mockMvc.perform(patch("/itens/{id}/preco", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"preco\":\"" + novoPreco + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarPreco(eq(id), eq(novoPreco));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar o disponibilidadeConsumoPresencial do item com sucesso")
//    void deveAtualizarDisponibilidadeConsumoPresencialItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        Boolean disponibilidadeConsumoPresencial = false;
//
//        // Mock do core controller
//        doNothing().when(itemCoreController).atualizarDisponibilidadeConsumoPresencial(
//                eq(id),
//                eq(disponibilidadeConsumoPresencial)
//        );
//
//        mockMvc.perform(patch("/itens/{id}/disponibilidade-consumo-presencial", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"isDisponivel\":\"" + disponibilidadeConsumoPresencial + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarDisponibilidadeConsumoPresencial(
//                eq(id),
//                eq(disponibilidadeConsumoPresencial)
//        );
//    }
//
//    @Test
//    @DisplayName("Deve atualizar o disponibilidade do item com sucesso")
//    void deveAtualizarDisponibilidade() throws Exception {
//        UUID id = UUID.randomUUID();
//        Boolean disponibilidade = false;
//
//        // Mock do core controller
//        doNothing().when(itemCoreController).atualizarDisponibilidade( eq(id), eq(disponibilidade));
//
//        mockMvc.perform(patch("/itens/{id}/disponibilidade", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"isDisponivel\":\"" + disponibilidade + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarDisponibilidade( eq(id), eq(disponibilidade));
//    }
//
//    @Test
//    @DisplayName("Deve atualizar a imagem do item com sucesso")
//    void deveAtualizarImagemDoItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        MockMultipartFile imagem = new MockMultipartFile("imagem", "imagem.jpg", "image/jpeg", "conteudo".getBytes());
//
//        doNothing().when(itemCoreController).atualizarImagem(eq(id), any(MultipartFile.class));
//
//        mockMvc.perform(multipart("/itens/{id}/imagem", id)
//                        .file(imagem)
//                        .with(request -> { request.setMethod("PATCH"); return request; }))
//                .andExpect(status().isNoContent());
//
//        verify(itemCoreController, times(1)).atualizarImagem(eq(id), any(MultipartFile.class));
//    }
//
//    @Test
//    @DisplayName("Deve baixar a imagem do item com sucesso")
//    void deveBaixarImagemDoItem() throws Exception {
//        UUID id = UUID.randomUUID();
//        String nomeArquivo = "imagem.jpg";
//        byte[] conteudo = "conteudo da imagem".getBytes();
//        ImagemCoreDto imagemCoreDto = new ImagemCoreDto(id, nomeArquivo, conteudo, "image/jpeg");
//
//        when(itemCoreController.baixarImagem(eq(id))).thenReturn(imagemCoreDto);
//
//        mockMvc.perform(get("/itens/{id}/imagem", id))
//                .andExpect(status().isOk())
//                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\""))
//                .andExpect(content().bytes(conteudo));
//
//        verify(itemCoreController, times(1)).baixarImagem(eq(id));
//    }
//
//}
