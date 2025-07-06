package br.com.fiapfood.unitario.services;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IUsuarioRepository;
import br.com.fiapfood.services.UsuarioService;
import br.com.fiapfood.services.exceptions.EmailDuplicadoException;
import br.com.fiapfood.services.interfaces.IEnderecoService;
import br.com.fiapfood.services.interfaces.IPerfilService;
import br.com.fiapfood.services.interfaces.IUsuarioService;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;
    @Mock
    private IPerfilService perfilService;
    @Mock
    private IEnderecoService enderecoService;
    private IUsuarioService usuarioService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, perfilService, enderecoService);
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarPorId {
        @Test
        void deveRetornarUsuarioRecordResponseQuandoIdExistir() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            UsuarioEntity usuarioEntity = validUsuarioEntity();

            when(usuarioRepository.recuperaDadosUsuarioPorId(id)).thenReturn(usuarioEntity);

            // Act
            UsuarioRecordResponse usuarioRecordResponse = usuarioService.buscarPorId(id);

            // Assert
            assertThat(usuarioRecordResponse).isNotNull();
            assertThat(usuarioRecordResponse.id()).isEqualTo(id);
            assertThat(usuarioRecordResponse.nome()).isEqualTo("John Doe");
            assertThat(usuarioRecordResponse.email()).isEqualTo("johndoe@email.com");
            assertThat(usuarioRecordResponse.ativo()).isTrue();
            assertThat(usuarioRecordResponse.dadosEndereco()).isNotNull();
            assertThat(usuarioRecordResponse.perfilAcesso()).isNotNull();
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioPorId(id);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

            when(usuarioRepository.recuperaDadosUsuarioPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.buscarPorId(id));

            assertThat(exception).isNotNull();
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioPorId(id);
        }
    }

    @Nested
    class BuscarUsuarios {
        @Test
        void deveRetornarUsuariosPaginados() {
            // Arrange
            Integer pagina = 1;

            List<UsuarioEntity> usuarios = List.of(validUsuarioEntity(), validUsuarioEntity(), validUsuarioEntity());
            Page<UsuarioEntity> fakePage = new PageImpl<>(usuarios);

            when(usuarioRepository.recuperaDadosUsuarios(pagina)).thenReturn(fakePage);

            UsuarioRecordPaginacaoResponse fakeResponse = mock(UsuarioRecordPaginacaoResponse.class);
            try (MockedStatic<UsuarioMapper> mapperMock = mockStatic(UsuarioMapper.class)) {
                mapperMock.when(() -> UsuarioMapper.toUsuario(fakePage)).thenReturn(fakeResponse);

                // Act
                UsuarioRecordPaginacaoResponse response = usuarioService.buscarUsuarios(pagina);

                // Assert
                assertThat(response).isEqualTo(fakeResponse);

                verify(usuarioRepository, times(1)).recuperaDadosUsuarios(pagina);
                mapperMock.verify(() -> UsuarioMapper.toUsuario(fakePage), times(1));
            }
        }
    }

    @Nested
    class Cadastrar {
        @Test
        void deveCadastrarUsuarioComDadosValidos() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = validUsuarioRecordRequest();

            doNothing().when(usuarioRepository).salvar(any(UsuarioEntity.class));
            when(usuarioRepository.emailJaCadastrado(anyString())).thenReturn(false);
            when(perfilService.existePorId(anyInt())).thenReturn(true);
            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.cadastrar(usuarioRecordRequest);

            // Assert
            verify(usuarioRepository, times(1)).emailJaCadastrado(anyString());
            verify(usuarioRepository, times(1)).salvar(captor.capture());
            verify(perfilService, times(1)).existePorId(anyInt());

            UsuarioEntity usuarioEntity = captor.getValue();
            assertThat(captor.getValue()).isNotNull();
            assertThat(captor.getValue()).isInstanceOf(UsuarioEntity.class);
            assertThat(usuarioEntity.getNome()).isEqualTo("John Doe");
            assertThat(usuarioEntity.getEmail()).isEqualTo("johndoe@email.com");
        }

        @Test
        void deveLancarEmailJaCadastradoExceptionQuantoEmailJaCadastrado() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = validUsuarioRecordRequest();

            when(usuarioRepository.emailJaCadastrado(anyString())).thenReturn(true);

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_EMAIL_DUPLICADO))
                        .thenReturn("Já existe um usuário com o email informado.");
                EmailDuplicadoException exception = assertThrows(EmailDuplicadoException.class, () -> usuarioService.cadastrar(usuarioRecordRequest));
                assertThat(exception.getMessage()).isEqualTo("Já existe um usuário com o email informado.");
            }

            verify(usuarioRepository, times(1)).emailJaCadastrado(anyString());
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
            verify(perfilService, times(0)).existePorId(anyInt());
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionQuantoPerfilNaoEncontrado() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequest = validUsuarioRecordRequest();

            when(usuarioRepository.emailJaCadastrado(anyString())).thenReturn(false);
            when(perfilService.existePorId(anyInt())).thenReturn(false);

            // Act & Assert
            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PERFIL_NAO_ENCONTRADO))
                        .thenReturn("Perfil não encontrado na base de dados.");

                PerfilNaoEncontradoException exception = assertThrows(PerfilNaoEncontradoException.class, () -> usuarioService.cadastrar(usuarioRecordRequest));
                assertThat(exception.getMessage()).isEqualTo("Perfil não encontrado na base de dados.");
            }

            verify(usuarioRepository, times(1)).emailJaCadastrado(anyString());
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
            verify(perfilService, times(1)).existePorId(anyInt());
        }
    }

    @Nested
    class AtualizarStatus {
        @Test
        void deveReativarUsuarioQuandoAnteriormenteInativo() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity inativeUsuarioEntity = validUsuarioEntity();
            inativeUsuarioEntity.setIsAtivo(false);

            when(usuarioRepository.recuperaDadosUsuarioInativoPorId(id)).thenReturn(inativeUsuarioEntity);

            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.atualizarStatus(id, true);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioInativoPorId(id);
            verify(usuarioRepository, times(1)).salvar(captor.capture());

            UsuarioEntity usuarioEntity = captor.getValue();
            assertThat(usuarioEntity).isNotNull();
            assertThat(usuarioEntity.getIsAtivo()).isTrue();
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionAoTentarReativarUsuarioQuandoAnteriormenteAtivo() {
            // Arrange
            UUID id = UUID.randomUUID();

            when(usuarioRepository.recuperaDadosUsuarioInativoPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados."));

            // Act
            UsuarioNaoEncontradoException exception =  assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarStatus(id, true));

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioInativoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
            assertThat(exception.getMessage()).isEqualTo("Não foram encontrados usuários na base de dados.");
        }

        @Test
        void deveDesativarUsuarioQuandoAnteriormenteAtivo() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity activeUsuarioEntity = validUsuarioEntity();

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(activeUsuarioEntity);

            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.atualizarStatus(id, false);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(1)).salvar(captor.capture());

            UsuarioEntity usuarioEntity = captor.getValue();
            assertThat(usuarioEntity).isNotNull();
            assertThat(usuarioEntity.getIsAtivo()).isFalse();
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionAoTentarInativarUsuarioQuandoAnteriormenteInativo() {
            // Arrange
            UUID id = UUID.randomUUID();

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados."));

            // Act
            UsuarioNaoEncontradoException exception =  assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarStatus(id, false));

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
            assertThat(exception.getMessage()).isEqualTo("Não foram encontrados usuários na base de dados.");
        }
    }

    @Nested
    class AtualizarPerfil {
        @Test
        void deveAtualizarPerfilSeDiferenteDoAtual() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();
            PerfilEntity novoPerfilEntity = new PerfilEntity(
                    2,
                    "Cliente"
            );

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(usuarioEntity);
            when(perfilService.buscarPorId(novoPerfilEntity.getId())).thenReturn(novoPerfilEntity);

            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.atualizarPerfil(id, 2);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(1)).salvar(captor.capture());
            verify(perfilService, times(1)).buscarPorId(novoPerfilEntity.getId());

            UsuarioEntity returnedUsuarioEntity = captor.getValue();
            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity.getPerfil().getId()).isEqualTo(novoPerfilEntity.getId());
        }

        @Test
        void naoDeveAtualizarPerfilSeIgualAoAtual() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(usuarioEntity);

            // Act
            usuarioService.atualizarPerfil(id, 1);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(usuarioEntity);
            verify(perfilService, times(0)).buscarPorId(anyInt());
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.randomUUID();

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarPerfil(id, 2));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
            verify(perfilService, times(0)).buscarPorId(anyInt());
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionSeNaoEncontrarNovoPerfilPorId() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(usuarioEntity);
            when(perfilService.buscarPorId(anyInt())).thenThrow(new PerfilNaoEncontradoException("Perfil não encontrado na base de dados."));

            // Act & Assert
            PerfilNaoEncontradoException exception = assertThrows(PerfilNaoEncontradoException.class, () -> usuarioService.atualizarPerfil(id, 2));

            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(usuarioEntity);
            verify(perfilService, times(1)).buscarPorId(anyInt());
            assertThat(exception.getMessage()).isEqualTo("Perfil não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarNome {
        @Test
        void deveAtualizarNome() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();
            String newName = "Jane Doe";

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(usuarioEntity);

            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.atualizarNome(id, newName);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(1)).salvar(captor.capture());

            UsuarioEntity returnedUsuarioEntity = captor.getValue();
            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity.getNome()).isEqualTo(newName);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.randomUUID();
            String newName = "Jane Doe";

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarNome(id, newName));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
        }
    }

    @Nested
    class AtualizarEmail {
        @Test
        void deveAtualizarEmail() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();
            String newEmail = "janedoe@email.com";

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenReturn(usuarioEntity);

            ArgumentCaptor<UsuarioEntity> captor = ArgumentCaptor.forClass(UsuarioEntity.class);

            // Act
            usuarioService.atualizarEmail(id, newEmail);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(1)).salvar(captor.capture());

            UsuarioEntity returnedUsuarioEntity = captor.getValue();
            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity.getEmail()).isEqualTo(newEmail);
        }

        @Test
        void deveLancarExceptionSeEmailJaCadastrado() {
            // Arrange
            UUID id = UUID.randomUUID();
            String newEmail = "janedoe@email.com";

            when(usuarioRepository.emailJaCadastrado(newEmail)).thenThrow(new EmailDuplicadoException("Já existe um usuário com o email informado."));

            // Act & Assert
            EmailDuplicadoException exception = assertThrows(EmailDuplicadoException.class, () -> usuarioService.atualizarEmail(id, newEmail));

            assertThat(exception.getMessage()).isEqualTo("Já existe um usuário com o email informado.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.randomUUID();
            String newEmail = "janedoe@email.com";

            when(usuarioRepository.recuperaDadosUsuarioAtivoPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarEmail(id, newEmail));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioAtivoPorId(id);
            verify(usuarioRepository, times(0)).salvar(any(UsuarioEntity.class));
        }
    }

    @Nested
    class BuscarUsuarioPorIdLogin {
        @Test
        void deveRetornarUsuarioPeloLoginId() {
            UUID loginId = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();

            when(usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginId)).thenReturn(usuarioEntity);

            var returnedUsuarioEntity = usuarioService.buscarUsuarioPorIdLogin(loginId);

            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity).isInstanceOf(UsuarioEntity.class);
            assertThat(returnedUsuarioEntity).isEqualTo(usuarioEntity);
            verify(usuarioRepository, times(1)).recuperarDadosUsuarioPorIdLogin(loginId);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionNaoEncontrarUsuarioAtravesDoLoginId() {
            // Arrange
            UUID loginId = UUID.randomUUID();

            when(usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginId)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.buscarUsuarioPorIdLogin(loginId));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperarDadosUsuarioPorIdLogin(loginId);
        }
    }

    @Nested
    class AtualizarDadosEndereco {
        @Test
        void deveAtualizarDadosEndereco() {
            // Arrange
            UUID id = UUID.randomUUID();
            UsuarioEntity usuarioEntity = validUsuarioEntity();
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            when(usuarioRepository.recuperaDadosUsuarioPorId(id)).thenReturn(usuarioEntity);
            doNothing().when(enderecoService).atualizarEndereco(usuarioEntity.getDadosEndereco(), enderecoRecordRequest);

            // Act
            usuarioService.atualizarDadosEndereco(id, enderecoRecordRequest);

            // Assert
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioPorId(id);
            verify(enderecoService, times(1)).atualizarEndereco(usuarioEntity.getDadosEndereco(), enderecoRecordRequest);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID id = UUID.randomUUID();
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            when(usuarioRepository.recuperaDadosUsuarioPorId(id)).thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado na base de dados."));

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarDadosEndereco(id, enderecoRecordRequest));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
            verify(usuarioRepository, times(1)).recuperaDadosUsuarioPorId(id);
            verify(enderecoService, times(0)).atualizarEndereco(any(EnderecoEntity.class), any(EnderecoRecordRequest.class));
        }
    }
}
