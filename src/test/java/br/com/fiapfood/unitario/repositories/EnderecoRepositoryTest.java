package br.com.fiapfood.unitario.repositories;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.repositories.impl.EnderecoRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IEnderecoJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validEnderecoEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EnderecoRepositoryTest {

    @Mock
    private IEnderecoJpaRepository enderecoJpaRepository;

    private EnderecoRepository enderecoRepository;

    AutoCloseable mock;

    private UUID enderecoId;

    private EnderecoEntity enderecoEntity;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        enderecoRepository = new EnderecoRepository(enderecoJpaRepository);
        enderecoEntity = validEnderecoEntity();
        enderecoId = UUID.randomUUID();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirBuscarPorId(){
        // Arrange
        enderecoEntity.setId(enderecoId);
        when(enderecoJpaRepository.findById(any(UUID.class))).thenReturn(Optional.of(enderecoEntity));

        // Act
        var enderecoEncontrado = enderecoRepository.buscarPorId(enderecoId);

        // Assert
        assertThat(enderecoEncontrado)
                .isNotNull()
                .containsSame(enderecoEntity);
        verify(enderecoJpaRepository, times(1)).findById(enderecoId);
    }

    @Test
    void devePermitirSalvarEndereco() {
        // Arrange
        when(enderecoJpaRepository.save(any(EnderecoEntity.class))).thenReturn(enderecoEntity);

        // Act
        enderecoRepository.salvar(enderecoEntity);

        // Assert
        assertThat(enderecoEntity).isNotNull();
        verify(enderecoJpaRepository, times(1)).save(enderecoEntity);
    }
}
