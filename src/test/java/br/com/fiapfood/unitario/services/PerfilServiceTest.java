package br.com.fiapfood.unitario.services;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.services.PerfilService;
import br.com.fiapfood.services.interfaces.IPerfilService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static br.com.fiapfood.utils.DataGenerator.validPerfilEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PerfilServiceTest {

    @Mock
    private IPerfilRepository perfilRepository;
    private IPerfilService perfilService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);

        perfilService = new PerfilService(perfilRepository);
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarPorId{
        @Test
        void deveRetornarPerfilQuandoIdExistir(){
            // Arrange
            PerfilEntity perfilEntity = validPerfilEntity();

            when(perfilRepository.buscarPorId(anyInt())).thenReturn(perfilEntity);

            // Act
            PerfilEntity returnedPerfil = perfilService.buscarPorId(1);

            // Assert
            assertThat(returnedPerfil).isInstanceOf(PerfilEntity.class);
            assertThat(returnedPerfil).isEqualTo(perfilEntity);
        }

        @Test
        void deveChamarRepositorioBuscarPorIdCorretamente(){
            // Arrange
            PerfilEntity perfilEntity = validPerfilEntity();

            when(perfilRepository.buscarPorId(anyInt())).thenReturn(perfilEntity);

            // Act
            perfilService.buscarPorId(1);

            // Assert
            verify(perfilRepository, times(1)).buscarPorId(anyInt());
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionSePerfilNaoEncontradoAtravesDoId(){
            // Arrange
            when(perfilRepository.buscarPorId(anyInt())).thenThrow(new PerfilNaoEncontradoException("Perfil não encontrado na base de dados."));

            // Act & Assert
            PerfilNaoEncontradoException exception = assertThrows(PerfilNaoEncontradoException.class, () -> perfilService.buscarPorId(3));
            assertThat(exception.getMessage()).isEqualTo("Perfil não encontrado na base de dados.");
        }
    }

    @Nested
    class BuscarTodos{
        @Test
        void deveRetornarListaDePerfisQuandoExistirem(){
            // Arrange
            List<PerfilEntity> perfilEntityList = new ArrayList<>(
                    List.of(validPerfilEntity(), validPerfilEntity(),validPerfilEntity())
            );

            when(perfilRepository.buscarTodos()).thenReturn(perfilEntityList);

            // Act
            List<PerfilRecordResponse> returnedPerfis = perfilService.buscarTodos();

            // Assert
            assertThat(returnedPerfis.size()).isEqualTo(perfilEntityList.size());
            assertThat(returnedPerfis.getFirst().id()).isEqualTo(perfilEntityList.getFirst().getId());
        }

        @Test
        void deveChamarRepositorioBuscarTodosCorretamente(){
            // Arrange
            List<PerfilEntity> perfilEntityList = new ArrayList<>(
                    List.of(validPerfilEntity(), validPerfilEntity(),validPerfilEntity())
            );

            when(perfilRepository.buscarTodos()).thenReturn(perfilEntityList);

            // Act
            perfilService.buscarTodos();

            // Assert
            verify(perfilRepository, times(1)).buscarTodos();
        }

        @Test
        void deveMapearCorretamenteOsPerfisParaRecordResponse(){
            // Arrange
            List<PerfilEntity> perfilEntityList = new ArrayList<>(
                    List.of(validPerfilEntity(), validPerfilEntity(),validPerfilEntity())
            );

            when(perfilRepository.buscarTodos()).thenReturn(perfilEntityList);

            // Act
            var returnedPerfilEntityList  = perfilService.buscarTodos();

            // Assert
            assertThat(returnedPerfilEntityList.getFirst()).isInstanceOf(PerfilRecordResponse.class);
            assertThat(returnedPerfilEntityList.getLast()).isInstanceOf(PerfilRecordResponse.class);
        }
    }
}
