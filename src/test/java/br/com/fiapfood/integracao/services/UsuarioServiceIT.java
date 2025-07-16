package br.com.fiapfood.integracao.services;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.exceptions.UsuarioNaoEncontradoException;
import br.com.fiapfood.repositories.interfaces.IUsuarioRepository;
import br.com.fiapfood.services.exceptions.EmailDuplicadoException;
import br.com.fiapfood.services.interfaces.IUsuarioService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsuarioServiceIT {

    @Autowired
    IUsuarioService usuarioService;
    @Autowired
    IUsuarioRepository usuarioRepository;

    @Nested
    class BuscarPorId {
        @Test
        void deveRetornarUsuarioRecordResponseQuandoIdExistir() {
            // Arrange
            UUID idCadastrado = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

            // Act
            UsuarioRecordResponse usuarioRecordResponse = usuarioService.buscarPorId(idCadastrado);

            // Assert
            assertThat(usuarioRecordResponse).isNotNull();
            assertThat(usuarioRecordResponse.id()).isEqualTo(idCadastrado);
            assertThat(usuarioRecordResponse.nome()).isEqualTo("Thiago Motta");
            assertThat(usuarioRecordResponse.email()).isEqualTo("thiago@fiapfood.com");
            assertThat(usuarioRecordResponse.ativo()).isTrue();
            assertThat(usuarioRecordResponse.dadosEndereco()).isNotNull();
            assertThat(usuarioRecordResponse.perfilAcesso()).isNotNull();
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID idNaoCadastrado = UUID.fromString("fc05db14-7993-4564-bff9-c258b5c738c7");

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.buscarPorId(idNaoCadastrado));
            assertThat(exception).isNotNull();
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }

    @Nested
    class BuscarUsuarios {
        @Test
        void deveRetornarUsuariosPaginados() {
            // Arrange
            Integer pagina = 1;

                // Act
                UsuarioRecordPaginacaoResponse response = usuarioService.buscarUsuarios(pagina);

                // Assert
                assertThat(response).isNotNull();
                assertThat(response).isInstanceOf(UsuarioRecordPaginacaoResponse.class);
                assertThat(response.dadosPaginacao()).isNotNull();
                assertThat(response.usuarios()).isNotNull();
                assertThat(response.usuarios().size()).isEqualTo(4);

            }
        }


    @Nested
    class Cadastrar {
        @Test
        void deveCadastrarUsuarioComDadosValidos() {
            // Arrange
            var usuariosInicial = usuarioRepository.recuperaDadosUsuarios(1);
            UsuarioRecordRequest usuarioRecordRequest = validUsuarioRecordRequest();

            // Act
            usuarioService.cadastrar(usuarioRecordRequest);
            var usuariosFinal = usuarioRepository.recuperaDadosUsuarios(1);

            // Assert
            assertThat(usuariosFinal.getTotalElements()).isEqualTo(usuariosInicial.getTotalElements() + 1);
        }

        @Test
        void deveLancarEmailJaCadastradoExceptionQuantoEmailJaCadastrado() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequestComEmailCadastrado = new UsuarioRecordRequest(
                    "John Doe",
                    "thiago@fiapfood.com",
                    1,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            EmailDuplicadoException exception = assertThrows(EmailDuplicadoException.class, () -> usuarioService.cadastrar(usuarioRecordRequestComEmailCadastrado));
            assertThat(exception.getMessage()).isEqualTo("Já existe um usuário com o email informado.");
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionQuantoPerfilNaoEncontrado() {
            // Arrange
            UsuarioRecordRequest usuarioRecordRequestComPerfilNaoCadastrado = new UsuarioRecordRequest(
                    "John Doe",
                    "johndoe@email.com",
                    3,
                    validEnderecoRecordRequest(),
                    validLoginRecordRequest()
            );

            // Act & Assert
            PerfilNaoEncontradoException exception = assertThrows(PerfilNaoEncontradoException.class, () -> usuarioService.cadastrar(usuarioRecordRequestComPerfilNaoCadastrado));
            assertThat(exception.getMessage()).isEqualTo("Perfil não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarStatus {
        @Test
        void deveReativarUsuarioQuandoAnteriormenteInativo() {
            // Arrange
            UUID idUsuarioInativo = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");

            // Act
            usuarioService.atualizarStatus(idUsuarioInativo, true);

            // Assert
            UsuarioEntity usuarioAtualizado = usuarioRepository.recuperaDadosUsuarioPorId(idUsuarioInativo);
            assertThat(usuarioAtualizado.getIsAtivo()).isTrue();
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionAoTentarReativarUsuarioQuandoAnteriormenteAtivo() {
            // Arrange
            UUID idUsuarioAtivo = UUID.fromString("de6762a9-e373-4a05-a6bb-d345a759b26f");

            // Act
            UsuarioNaoEncontradoException exception =  assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarStatus(idUsuarioAtivo, true));

            // Assert
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }

        @Test
        void deveDesativarUsuarioQuandoAnteriormenteAtivo() {
            // Arrange
            UUID idUsuarioAtivo = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");

            // Act
            usuarioService.atualizarStatus(idUsuarioAtivo, false);

            // Assert
            UsuarioEntity usuarioAtualizado = usuarioRepository.recuperaDadosUsuarioPorId(idUsuarioAtivo);
            assertThat(usuarioAtualizado.getIsAtivo()).isFalse();
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionAoTentarInativarUsuarioQuandoAnteriormenteInativo() {
            // Arrange
            UUID idUsuarioInativo = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");

            // Act
            UsuarioNaoEncontradoException exception =  assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarStatus(idUsuarioInativo, false));

            // Assert
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarPerfil {
        @Test
        void deveAtualizarPerfilSeDiferenteDoAtual() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            UsuarioEntity usuarioInicial = usuarioRepository.recuperaDadosUsuarioPorId(id);

            // Act
            usuarioService.atualizarPerfil(id, 2);

            // Assert
            UsuarioEntity usuarioAtualizado = usuarioRepository.recuperaDadosUsuarioPorId(id);
            assertThat(usuarioAtualizado.getPerfil().getId()).isNotEqualTo(usuarioInicial.getPerfil().getId());
            assertThat(usuarioAtualizado.getPerfil().getId()).isEqualTo(2);
        }

        @Test
        void naoDeveAtualizarPerfilSeIgualAoAtual() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            UsuarioEntity usuarioInicial = usuarioRepository.recuperaDadosUsuarioPorId(id);

            // Act
            usuarioService.atualizarPerfil(id, 1);

            // Assert
            UsuarioEntity usuarioAtualizado = usuarioRepository.recuperaDadosUsuarioPorId(id);
            assertThat(usuarioInicial.getPerfil()).isEqualTo(usuarioAtualizado.getPerfil());
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarPerfil(id, 2));
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }

        @Test
        void deveLancarPerfilNaoEncontradoExceptionSeNaoEncontrarNovoPerfilPorId() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");

            // Act & Assert
            PerfilNaoEncontradoException exception = assertThrows(PerfilNaoEncontradoException.class, () -> usuarioService.atualizarPerfil(id, 3));
            assertThat(exception.getMessage()).isEqualTo("Perfil não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarNome {
        @Test
        void deveAtualizarNome() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            UsuarioEntity usuarioInicial = usuarioRepository.recuperaDadosUsuarioPorId(id);
            String newName = "Jane Doe";

            // Act
            usuarioService.atualizarNome(id, newName);

            // Assert
            UsuarioEntity usuarioNovo = usuarioRepository.recuperaDadosUsuarioPorId(id);
            assertThat(usuarioNovo.getNome()).isEqualTo(newName);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("78c76348-d821-4808-ac67-a5e599191b23");
            String newName = "Jane Doe";

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarNome(id, newName));

            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarEmail {
        @Test
        void deveAtualizarEmail() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            String newEmail = "janedoe@email.com";

            // Act
            usuarioService.atualizarEmail(id, newEmail);

            // Assert
            UsuarioEntity returnedUsuarioEntity = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity.getEmail()).isEqualTo(newEmail);
        }

        @Test
        void deveLancarExceptionSeEmailJaCadastrado() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c");
            String newEmail = "carla.rodrigues@fiapfood.com";

            // Act & Assert
            EmailDuplicadoException exception = assertThrows(EmailDuplicadoException.class, () -> usuarioService.atualizarEmail(id, newEmail));
            assertThat(exception.getMessage()).isEqualTo("Já existe um usuário com o email informado.");
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioAtivoPorId() {
            // Arrange
            UUID id = UUID.fromString("602a4056-68d0-44f0-8284-14b0cf7a75b6");
            String newEmail = "janedoe@email.com";

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarEmail(id, newEmail));
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }

    @Nested
    class BuscarUsuarioPorIdLogin {
        @Test
        void deveRetornarUsuarioPeloLoginId() {
            UUID loginId = UUID.fromString("dbc8695f-fe37-4741-9c6d-7bf5e96dfe6d");

            var returnedUsuarioEntity = usuarioService.buscarUsuarioPorIdLogin(loginId);

            assertThat(returnedUsuarioEntity).isNotNull();
            assertThat(returnedUsuarioEntity).isInstanceOf(UsuarioEntity.class);
            assertThat(returnedUsuarioEntity.getDadosLogin().getId()).isEqualTo(loginId);
        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionNaoEncontrarUsuarioAtravesDoLoginId() {
            // Arrange
            UUID wrongLoginId = UUID.fromString("bcd8695f-fe37-4741-9c6d-7bf5e96dfed6");

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.buscarUsuarioPorIdLogin(wrongLoginId));
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }

    @Nested
    class AtualizarDadosEndereco {
        @Test
        void deveAtualizarDadosEndereco() {
            // Arrange
            UUID id = UUID.fromString("b48bc2dc-fd87-462d-a8a6-6e74674d0338");
            UsuarioEntity usuarioEntityInicial = usuarioRepository.recuperaDadosUsuarioPorId(id);
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            // Act
            usuarioService.atualizarDadosEndereco(id, enderecoRecordRequest);

            // Assert
            UsuarioEntity usuarioEntity = usuarioRepository.recuperaDadosUsuarioPorId(id);
            EnderecoEntity enderecoAtualizado = usuarioEntity.getDadosEndereco();
            assertThat(enderecoAtualizado.getId()).isEqualTo(usuarioEntity.getDadosEndereco().getId());
            assertThat(enderecoAtualizado.getCidade()).isEqualTo(enderecoRecordRequest.cidade());
            assertThat(enderecoAtualizado.getBairro()).isEqualTo(enderecoRecordRequest.bairro());
            assertThat(enderecoAtualizado.getEstado()).isEqualTo(enderecoRecordRequest.estado());
            assertThat(enderecoAtualizado.getEndereco()).isEqualTo(enderecoRecordRequest.endereco());
            assertThat(enderecoAtualizado.getNumero()).isEqualTo(enderecoRecordRequest.numero());
            assertThat(enderecoAtualizado.getSemNumero()).isEqualTo(enderecoRecordRequest.semNumero());
            assertThat(enderecoAtualizado.getCep()).isEqualTo(enderecoRecordRequest.cep());
            assertThat(enderecoAtualizado.getComplemento()).isEqualTo(enderecoRecordRequest.complemento());

        }

        @Test
        void deveLancarUsuarioNaoEncontradoExceptionSeNaoEncontrarUsuarioPorId() {
            // Arrange
            UUID id = UUID.fromString("cf05db14-7993-4564-bff9-c258b5c73c78");
            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            // Act & Assert
            UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.atualizarDadosEndereco(id, enderecoRecordRequest));
            assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado na base de dados.");
        }
    }
}
