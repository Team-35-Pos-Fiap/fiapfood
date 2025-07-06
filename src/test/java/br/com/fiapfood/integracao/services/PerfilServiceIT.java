package br.com.fiapfood.integracao.services;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.services.interfaces.IPerfilService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PerfilServiceIT {

    @Autowired
    private IPerfilService perfilService;

    @Nested
    class BuscarPorId{
        @Test
        void deveRetornarPerfilQuandoIdExistir(){
            // Arrange

            // Act
            PerfilEntity returnedPerfil = perfilService.buscarPorId(1);

            // Assert
            assertThat(returnedPerfil).isInstanceOf(PerfilEntity.class);
            assertThat(returnedPerfil.getId()).isEqualTo(1);
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionSePerfilNaoEncontradoAtravesDoId(){
            // Arrange

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

            // Act
            List<PerfilRecordResponse> returnedPerfis = perfilService.buscarTodos();

            // Assert
            assertThat(returnedPerfis.size()).isEqualTo(2);
            assertThat(returnedPerfis)
                    .extracting(PerfilRecordResponse::id)
                    .containsExactly(1, 2);
        }

        @Test
        void deveMapearCorretamenteOsPerfisParaRecordResponse(){
            // Arrange

            // Act
            var returnedPerfilEntityList  = perfilService.buscarTodos();

            // Assert
            assertThat(returnedPerfilEntityList.getFirst()).isInstanceOf(PerfilRecordResponse.class);
            assertThat(returnedPerfilEntityList.getLast()).isInstanceOf(PerfilRecordResponse.class);
        }
    }
}
