package br.com.fiapfood.infrastructure.controllers.unitarios;

import br.com.fiapfood.core.controllers.interfaces.ILoginCoreController;
import br.com.fiapfood.core.entities.dto.LoginDto;
import br.com.fiapfood.infraestructure.controllers.LoginController;
import br.com.fiapfood.infraestructure.controllers.exceptions.ErrorHandler;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static br.com.fiapfood.utils.JsonToString.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ILoginCoreController loginCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        LoginController loginController = new LoginController(loginCoreController);

        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController)
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
    class ValidarLoginRequest {

        @DisplayName("Validação de login com sucesso")
        @Test
        void devePermitirValidarLogin() throws Exception {
            // Arrange
            LoginDto loginRecordRequest = new LoginDto(UUID.randomUUID(),"us0001", "123");

            when(loginCoreController.validar(any(LoginDto.class)))
                    .thenReturn("Acesso liberado");

            // Act & Assert
            mockMvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRecordRequest)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.mensagem").value("Acesso liberado"));
            verify(loginCoreController, times(1)).validar(any(LoginDto.class));
        }
    }
}
