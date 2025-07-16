package br.com.fiapfood.unitario.repositories;

import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.impl.UsuarioRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IUsuarioJpaRepository;
import br.com.fiapfood.services.exceptions.PaginaInvalidaException;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validUsuarioEntity;
import static org.mockito.ArgumentMatchers.anyString;

public class UsuarioRepositoryTest {

    @Mock
    private IUsuarioJpaRepository usuarioJpaRepository;

    private UsuarioRepository usuarioRepository;

    AutoCloseable mock;

    private UsuarioEntity usuarioEntity;

    private UUID usuarioId;

    private UUID loginId;

    private MockedStatic<MensagensUtil> mensagemUtilMock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        mensagemUtilMock = Mockito.mockStatic(MensagensUtil.class);
        usuarioRepository = new UsuarioRepository(usuarioJpaRepository);
        usuarioEntity = validUsuarioEntity();
        usuarioId = UUID.randomUUID();
        loginId = UUID.randomUUID();

        mensagemUtilMock.when(() -> MensagensUtil.recuperarMensagem(anyString()))
                .thenReturn("Usuário não encontrado na base de dados.");

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
        mensagemUtilMock.close();
    }

    @Test
    void devePermitirBuscarUsuarioPorId() {
        // Arrange
        when(usuarioJpaRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        // Act
        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperaDadosUsuarioPorId(usuarioId);

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado).isEqualTo(usuarioEntity);
        verify(usuarioJpaRepository, times(1)).findById(usuarioId);
    }

    @Test
    void deveLancarUsuarioNaoEncontradoExceptionSeUsuarioNaoForEncontrado() {
        // Arrange
        when(usuarioJpaRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioPorId(usuarioId))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage("Usuário não encontrado na base de dados.");
        verify(usuarioJpaRepository, times(1)).findById(usuarioId);
    }

    @Test
    void devePermitirBuscarUsuarioInativoPorId() {
        // Arrange
        usuarioEntity.setIsAtivo(false);
        when(usuarioJpaRepository.findByIdAndIsAtivoFalse(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        // Act
        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperaDadosUsuarioInativoPorId(usuarioId);

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado).isEqualTo(usuarioEntity);
        assertThat(usuarioEncontrado.getIsAtivo()).isFalse();
        verify(usuarioJpaRepository, times(1)).findByIdAndIsAtivoFalse(usuarioId);
    }

    @Test
    void deveLancarUsuarioNaoEncontradoExceptionSeUsuarioInativoNaoForEncontrado() {
        // Arrange
        when(usuarioJpaRepository.findByIdAndIsAtivoFalse(usuarioId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioInativoPorId(usuarioId))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage("Usuário não encontrado na base de dados.");
        verify(usuarioJpaRepository, times(1)).findByIdAndIsAtivoFalse(usuarioId);
    }

    @Test
    void devePermitirBuscarUsuarioAtivoPorId() {
        // Arrange
        when(usuarioJpaRepository.findByIdAndIsAtivoTrue(usuarioId)).thenReturn(Optional.of(usuarioEntity));

        // Act
        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperaDadosUsuarioAtivoPorId(usuarioId);

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado).isEqualTo(usuarioEntity);
        assertThat(usuarioEncontrado.getIsAtivo()).isTrue();
        verify(usuarioJpaRepository, times(1)).findByIdAndIsAtivoTrue(usuarioId);
    }

    @Test
    void deveLancarUsuarioNaoEncontradoExceptionSeUsuarioAtivoNaoForEncontrado() {
        // Arrange
        when(usuarioJpaRepository.findByIdAndIsAtivoTrue(usuarioId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarioAtivoPorId(usuarioId))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage("Usuário não encontrado na base de dados.");
        verify(usuarioJpaRepository, times(1)).findByIdAndIsAtivoTrue(usuarioId);
    }

    @Test
    void devePermitirBuscarUsuarioPorIdLogin() {
        // Arrange
        when(usuarioJpaRepository.findByIdLogin(loginId)).thenReturn(Optional.of(usuarioEntity));

        // Act
        UsuarioEntity usuarioEncontrado = usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginId);

        // Assert
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado).isEqualTo(usuarioEntity);
        verify(usuarioJpaRepository, times(1)).findByIdLogin(loginId);
    }

    @Test
    void deveLancarUsuarioNaoEncontradoExceptionSeUsuarioNaoForEncontradoPorIdLogin() {
        // Arrange
        when(usuarioJpaRepository.findByIdLogin(loginId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginId))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage("Usuário não encontrado na base de dados.");
        verify(usuarioJpaRepository, times(1)).findByIdLogin(loginId);
    }

    @Test
    void devePermitirBuscarUsuariosComPaginacao() {
        // Arrange
        List<UsuarioEntity> usuarios = List.of(usuarioEntity);
        Page<UsuarioEntity> paginaUsuarios = new PageImpl<>(usuarios);
        when(usuarioJpaRepository.findAll(any(PageRequest.class))).thenReturn(paginaUsuarios);

        // Act
        Page<UsuarioEntity> usuariosDaPagina = usuarioRepository.recuperaDadosUsuarios(1);

        // Assert
        assertThat(usuariosDaPagina).isNotNull();
        assertThat(usuariosDaPagina.getContent()).hasSize(1);
        assertThat(usuariosDaPagina.getContent().get(0)).isEqualTo(usuarioEntity);
    }

    @Test
    void deveLancarPaginaInvalidaExceptionSePaginaForMenorQueUm() {
        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(0))
                .isInstanceOf(PaginaInvalidaException.class);

        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(-1))
                .isInstanceOf(PaginaInvalidaException.class);

        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(null))
                .isInstanceOf(PaginaInvalidaException.class);
    }

    @Test
    void deveLancarUsuarioNaoEncontradoExceptionSeNenhumUsuarioEncontrado() {
        // Arrange
        Page<UsuarioEntity> paginaVazia = new PageImpl<>(Collections.emptyList());
        when(usuarioJpaRepository.findAll(any(PageRequest.class))).thenReturn(paginaVazia);

        // Act + Assert
        assertThatThrownBy(() -> usuarioRepository.recuperaDadosUsuarios(1))
                .isInstanceOf(UsuarioNaoEncontradoException.class)
                .hasMessage("Usuário não encontrado na base de dados.");
    }

    @Test
    void devePermitirSalvarUsuario() {
        // Act
        usuarioRepository.salvar(usuarioEntity);

        // Assert
        verify(usuarioJpaRepository, times(1)).save(usuarioEntity);
    }

    @Test
    void deveRetornarTrueSeEmailJaCadastrado() {
        // Arrange
        String email = "test@example.com";
        when(usuarioJpaRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean resultado = usuarioRepository.emailJaCadastrado(email);

        // Assert
        assertThat(resultado).isTrue();
    }

    @Test
    void deveRetornarFalseSeEmailNaoCadastrado() {
        // Arrange
        String email = "test@example.com";
        when(usuarioJpaRepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean resultado = usuarioRepository.emailJaCadastrado(email);

        // Assert
        assertThat(resultado).isFalse();
        verify(usuarioJpaRepository, times(1)).existsByEmail(email);
    }
}
