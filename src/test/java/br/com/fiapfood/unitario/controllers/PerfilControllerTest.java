package br.com.fiapfood.unitario.controllers;

import br.com.fiapfood.controllers.PerfilController;
import br.com.fiapfood.controllers.exceptions.ErrorHandler;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.services.interfaces.IPerfilService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PerfilControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IPerfilService perfilService;

    AutoCloseable mock;

    @BeforeEach
    public void setup() {
        mock = MockitoAnnotations.openMocks(this);
        PerfilController perfilController = new PerfilController(perfilService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(perfilController)
                .setControllerAdvice(new ErrorHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarTodosRequest {

        @Test
        void deveRetornarListaComPerfisCadastrados() throws Exception {
            // Arrange
            List<PerfilRecordResponse> perfis = List.of(
                    new PerfilRecordResponse(1, "Admin"),
                    new PerfilRecordResponse(2, "User")
            );

            when(perfilService.buscarTodos()).thenReturn(perfis);

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

            verify(perfilService, times(1)).buscarTodos();
        }
    }
}
