package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.IPerfilCoreController;
import br.com.fiapfood.core.entities.dto.PerfilDto;
import br.com.fiapfood.core.exceptions.PerfilInvalidoException;
import br.com.fiapfood.infraestructure.controllers.PerfilController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PerfilControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IPerfilCoreController perfilCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        PerfilController perfilController = new PerfilController(perfilCoreController);

        this.mockMvc = MockMvcBuilders.standaloneSetup(perfilController)
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

    @Nested
    class BuscarTodosRequest {
        @DisplayName("Buscar todos os perfis cadastrados")
        @Test
        void deveRetornarListaComPerfisCadastrados() throws Exception {
            // Arrange
            List<PerfilDto> perfis = List.of(
                    new PerfilDto(1, "Admin"),
                    new PerfilDto(2, "User")
            );

            when(perfilCoreController.buscarTodos()).thenReturn(perfis);

            // Act & Assert
            mockMvc.perform(get("/perfis")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].nome").value("Admin"))
                    .andExpect(jsonPath("$[1].id").value(2))
                    .andExpect(jsonPath("$[1].nome").value("User"));

            verify(perfilCoreController, times(1)).buscarTodos();
        }
    }

    @Nested
    class BuscarPorIdRequest {
        @DisplayName("Buscar perfil por id com sucesso")
        @Test
        void deveRetornarPerfilPorId() throws Exception {
            // Arrange
            int perfilId = 1;
            PerfilDto perfil = new PerfilDto(1, "Admin");

            when(perfilCoreController.buscarPorId(anyInt())).thenReturn(perfil);

            // Act & Assert
            mockMvc.perform(get("/perfis/{id}", perfilId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.nome").value("Admin"))
                    .andExpect(status().isOk());

            verify(perfilCoreController, times(1)).buscarPorId(anyInt());
        }

        @DisplayName("Buscar perfil por id com erro. Perfil nao encontrado através do id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarPerfilPorId() throws Exception {
            // Arrange
            int perfilId = 3;

            when(perfilCoreController.buscarPorId(anyInt())).thenThrow(new PerfilInvalidoException("Não foi encontrado nenhum perfil com o id informado."));

            // Act & Assert
            mockMvc.perform(get("/perfis/{id}", perfilId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum perfil com o id informado."));

            verify(perfilCoreController, times(1)).buscarPorId(anyInt());
        }
    }
}
