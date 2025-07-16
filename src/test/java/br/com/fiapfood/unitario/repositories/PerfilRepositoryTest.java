package br.com.fiapfood.unitario.repositories;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.impl.PerfilRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IPerfilJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static br.com.fiapfood.utils.DataGenerator.validPerfilEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PerfilRepositoryTest {

    @Mock
    private IPerfilJpaRepository perfilJpaRepository;

    private PerfilRepository perfilRepository;

    AutoCloseable mock;

    private Integer perfilId;

    private PerfilEntity perfilEntity;

    private MockedStatic<MensagensUtil> mensagemUtilMock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemUtilMock = Mockito.mockStatic(MensagensUtil.class);
        perfilRepository = new PerfilRepository(perfilJpaRepository);
        perfilEntity = validPerfilEntity();
        perfilId = 1;

        mensagemUtilMock.when(() -> MensagensUtil.recuperarMensagem(anyString()))
                .thenReturn("Perfil não encontrado na base de dados.");
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
        mensagemUtilMock.close();
    }

    @Test
    void devePermitirBuscarPorId() {
        // Arrange
        perfilEntity.setId(perfilId);
        when(perfilJpaRepository.findById(any(Integer.class))).thenReturn(Optional.of(perfilEntity));

        // Act
        var loginEncontrado = perfilRepository.buscarPorId(perfilId);

        // Assert
        assertThat(loginEncontrado)
                .isNotNull()
                .isEqualTo(perfilEntity);
        assertThat(loginEncontrado.getId()).isEqualTo(perfilId);
        assertThat(loginEncontrado.getNome()).isEqualTo(perfilEntity.getNome());
        verify(perfilJpaRepository, times(1)).findById(perfilId);
    }

    @Test
    void deveLancarPerfilNaoEncontradoExceptionSeNaoEcontrarPerfil() {
        // Arrange
        when(perfilJpaRepository.findById(perfilId)).thenReturn(Optional.empty());
        // Act + Assert
        assertThatThrownBy(() -> perfilRepository.buscarPorId(perfilId))
                .isInstanceOf(PerfilNaoEncontradoException.class)
                .hasMessage("Perfil não encontrado na base de dados.");
        verify(perfilJpaRepository, times(1)).findById(perfilId);
        mensagemUtilMock.verify(() ->
                        MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PERFIL_NAO_ENCONTRADO),
                times(1));
    }

    @Test
    void deveBuscarTodosPerfisCadastrados() {
        // Arrange
        List<PerfilEntity> listaPerfis = Arrays.asList(
                new PerfilEntity(1, "Dono"),
                new PerfilEntity(2, "Cliente")
        );
        when(perfilJpaRepository.findAll()).thenReturn(listaPerfis);

        // Act
        List<PerfilEntity> perfisEncontrados = perfilRepository.buscarTodos();

        // Assert
        assertThat(perfisEncontrados).isNotNull();
        assertThat(perfisEncontrados).isNotEmpty();
        assertThat(perfisEncontrados).hasSize(2);
        assertThat(perfisEncontrados).containsExactlyElementsOf(listaPerfis);
        assertThat(perfisEncontrados.get(0).getId()).isEqualTo(1);
        assertThat(perfisEncontrados.get(1).getId()).isEqualTo(2);
        verify(perfilJpaRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarListaVaziaCasoNenhumPerfilCadastrado() {
        // Arrange
        when(perfilJpaRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<PerfilEntity> perfisEncontrados = perfilRepository.buscarTodos();

        // Assert
        assertThat(perfisEncontrados).isNotNull();
        assertThat(perfisEncontrados).isEmpty();
        verify(perfilJpaRepository, times(1)).findAll();
    }

    @Test
    void deveChecarSeIdPerfilCadastrado() {
        // Arrange
        when(perfilJpaRepository.existsById(perfilId)).thenReturn(true);

        // Act
        boolean resultado = perfilRepository.idJaCadastrado(perfilId);

        // Assert
        assertThat(resultado).isTrue();
        verify(perfilJpaRepository, times(1)).existsById(perfilId);
    }

    @Test
    void deveRetornarFalseQuandoIdPerfilNaoEstaCadastrado() {
        // Arrange
        when(perfilJpaRepository.existsById(perfilId)).thenReturn(false);

        // Act
        boolean resultado = perfilRepository.idJaCadastrado(perfilId);

        // Assert
        assertThat(resultado).isFalse();
        verify(perfilJpaRepository, times(1)).existsById(perfilId);
    }
}
