package br.com.fiapfood.core.controllers.unitarios;

import br.com.fiapfood.core.controllers.impl.UsuarioCoreController;
import br.com.fiapfood.core.controllers.interfaces.IUsuarioCoreController;
import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.CadastrarUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoCoreDto;
import br.com.fiapfood.core.exceptions.perfil.PerfilNaoEncontradoException;
import br.com.fiapfood.core.exceptions.usuario.*;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarMatriculaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarSenhaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IValidarAcessoUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.*;
import br.com.fiapfood.infraestructure.controllers.request.usuario.CadastrarUsuarioDto;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;

import static br.com.fiapfood.utils.DtoDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioCoreControllerTest {

    @Mock
    private IBuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @Mock
    private IBuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;
    @Mock
    private ICadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    @Mock
    private IAtualizarEmailUsuarioUseCase atualizarEmailUsuarioUseCase;
    @Mock
    private IAtualizarNomeUsuarioUseCase atualizarNomeUsuarioUseCase;
    @Mock
    private IInativarUsuarioUseCase inativarUsuarioUseCase;
    @Mock
    private IReativarUsuarioUseCase reativarUsuarioUseCase;
    @Mock
    private IAtualizarPerfilUsuarioUseCase atualizarPerfilUsuarioUseCase;
    @Mock
    private IAtualizarEnderecoUsuarioUseCase atualizarEnderecoUsuarioUseCase;
    @Mock
    private IValidarAcessoUseCase validarAcessoUseCase;
    @Mock
    private IAtualizarSenhaUseCase atualizarSenhaUseCase;
    @Mock
    private IAtualizarMatriculaUseCase atualizarMatriculaUseCase;
    private IUsuarioCoreController usuarioCoreController;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        usuarioCoreController = new UsuarioCoreController(
                buscarUsuarioPorIdUseCase,
                buscarTodosUsuariosUseCase,
                cadastrarUsuarioUseCase,
                atualizarEmailUsuarioUseCase,
                atualizarNomeUsuarioUseCase
                ,inativarUsuarioUseCase
                ,reativarUsuarioUseCase
                ,atualizarPerfilUsuarioUseCase,
                atualizarEnderecoUsuarioUseCase,
                validarAcessoUseCase,
                atualizarSenhaUseCase,
                atualizarMatriculaUseCase
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarUsuarioRequest {
        @DisplayName("Cadastrar novo usuário chamando o use case com sucesso")
        @Test
        void devePermitirCadastrarUsuarioComSucesso(){
            // Arrange
            CadastrarUsuarioDto dadosUsuarioController = cadastrarUsuarioDtoValido();
            CadastrarUsuarioCoreDto dadosUsuarioUseCase = cadastrarUsuarioCoreDtoValido();

            doNothing().when(cadastrarUsuarioUseCase).cadastrar(any(CadastrarUsuarioCoreDto.class));
            ArgumentCaptor<CadastrarUsuarioCoreDto> captor = ArgumentCaptor.forClass(CadastrarUsuarioCoreDto.class);

            // Act
            usuarioCoreController.cadastrar(dadosUsuarioController);

            // Assert
            verify(cadastrarUsuarioUseCase, times(1)).cadastrar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(dadosUsuarioUseCase);
        }

        @DisplayName("Cadastrar novo usuário chamando o use case com erro. Email já cadastrado")
        @Test
        void deveLancarExcecaoSeEmailJaCadastrado(){
            // Arrange
            CadastrarUsuarioDto dadosUsuarioController = cadastrarUsuarioDtoValido();

            doThrow(new EmailDuplicadoException("Já existe um usuário com o email informado.")).when(cadastrarUsuarioUseCase).cadastrar(any(CadastrarUsuarioCoreDto.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.cadastrar(dadosUsuarioController))
                    .isInstanceOf(EmailDuplicadoException.class)
                    .hasMessage("Já existe um usuário com o email informado.");
            verify(cadastrarUsuarioUseCase, times(1)).cadastrar(any(CadastrarUsuarioCoreDto.class));
        }

        @DisplayName("Cadastrar novo usuário chamando o use case com erro. Matricula já cadastrada")
        @Test
        void deveLancarExcecaoSeMatriculaJaCadastrado(){
            // Arrange
            CadastrarUsuarioDto dadosUsuarioController = cadastrarUsuarioDtoValido();

            doThrow(new MatriculaDuplicadaException("Já existe um usuário com a matrícula informada.")).when(cadastrarUsuarioUseCase).cadastrar(any(CadastrarUsuarioCoreDto.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.cadastrar(dadosUsuarioController))
                    .isInstanceOf(MatriculaDuplicadaException.class)
                    .hasMessage("Já existe um usuário com a matrícula informada.");
            verify(cadastrarUsuarioUseCase, times(1)).cadastrar(any(CadastrarUsuarioCoreDto.class));
        }

        @DisplayName("Cadastrar novo usuário chamando o use case com erro. Perfil informado não cadastrado.")
        @Test
        void deveLancarExcecaoSePerfilNaoCadstrado(){
            // Arrange
            CadastrarUsuarioDto dadosUsuarioController = cadastrarUsuarioDtoValido();

            doThrow(new PerfilNaoEncontradoException("Não foi encontrado nenhum perfil com o id informado.")).when(cadastrarUsuarioUseCase).cadastrar(any(CadastrarUsuarioCoreDto.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.cadastrar(dadosUsuarioController))
                    .isInstanceOf(PerfilNaoEncontradoException.class)
                    .hasMessage("Não foi encontrado nenhum perfil com o id informado.");
            verify(cadastrarUsuarioUseCase, times(1)).cadastrar(any(CadastrarUsuarioCoreDto.class));
        }
    }

    @Nested
    class InativarUsuarioRequest {
        @DisplayName("Inativar usuário chamando use case com sucesso.")
        @Test
        void devePermitirInativarUsuarioComSucesso(){
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(inativarUsuarioUseCase).inativar(any(UUID.class));
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            usuarioCoreController.inativar(id);

            // Assert
            verify(inativarUsuarioUseCase, times(1)).inativar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(id);
        }

        @DisplayName("Inativar usuário chamando o use case com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(inativarUsuarioUseCase).inativar(any(UUID.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.inativar(id))
                    .isInstanceOf(UsuarioNaoEncontradoException.class)
                    .hasMessage("Não foi encontrado nenhum usuário com o id informado.");
            verify(inativarUsuarioUseCase, times(1)).inativar(any(UUID.class));
        }

        @DisplayName("Inativar usuário chamando o use case com erro. Usuário já está inativo.")
        @Test
        void deveLancarExcecaoSeUsuarioJaEstiverInativo(){
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível inativar o usuário pois ele já se encontra inativo.")).when(inativarUsuarioUseCase).inativar(any(UUID.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.inativar(id))
                    .isInstanceOf(AtualizacaoStatusUsuarioNaoPermitidaException.class)
                    .hasMessage("Não é possível inativar o usuário pois ele já se encontra inativo.");
            verify(inativarUsuarioUseCase, times(1)).inativar(any(UUID.class));
        }
    }

    @Nested
    class AtivarUsuarioRequest {
        @DisplayName("Ativar usuário chamando use case com sucesso.")
        @Test
        void devePermitirReativarUsuarioComSucesso(){
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(reativarUsuarioUseCase).reativar(any(UUID.class));
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            usuarioCoreController.reativar(id);

            // Assert
            verify(reativarUsuarioUseCase, times(1)).reativar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(id);
        }

        @DisplayName("Reativar usuário chamando o use case com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(reativarUsuarioUseCase).reativar(any(UUID.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.reativar(id))
                    .isInstanceOf(UsuarioNaoEncontradoException.class)
                    .hasMessage("Não foi encontrado nenhum usuário com o id informado.");
            verify(reativarUsuarioUseCase, times(1)).reativar(any(UUID.class));
        }

        @DisplayName("Reativar usuário chamando o use case com erro. Usuário já está inativo.")
        @Test
        void deveLancarExcecaoSeUsuarioJaEstiverInativo(){
            // Arrange
            UUID id = UUID.randomUUID();

            doThrow(new AtualizacaoStatusUsuarioNaoPermitidaException("Não é possível inativar o usuário pois ele já se encontra inativo.")).when(reativarUsuarioUseCase).reativar(any(UUID.class));

            //Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.reativar(id))
                    .isInstanceOf(AtualizacaoStatusUsuarioNaoPermitidaException.class)
                    .hasMessage("Não é possível inativar o usuário pois ele já se encontra inativo.");
            verify(reativarUsuarioUseCase, times(1)).reativar(any(UUID.class));
        }
    }

    @Nested
    class BuscarUsuarioPorIdRequest {

        @DisplayName("Busca usuário por id chamando o use case com sucesso.")
        @Test
        void devePermitirBuscarUsuarioPorId(){
            // Arrange
            DadosUsuarioCoreDto usuarioEsperado = dadosUsuarioCoreDtoValido();
            UUID id = usuarioEsperado.id();

            when(buscarUsuarioPorIdUseCase.buscar(any(UUID.class)))
                    .thenReturn(usuarioEsperado);
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            var usuarioRetornadoDoController = usuarioCoreController.buscarUsuarioPorId(id);

            // Assert
            verify(buscarUsuarioPorIdUseCase, times(1)).buscar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(id);
            assertThat(usuarioEsperado.id()).isEqualTo(usuarioRetornadoDoController.id());
            assertThat(usuarioEsperado.nome()).isEqualTo(usuarioRetornadoDoController.nome());
            assertThat(usuarioEsperado.email()).isEqualTo(usuarioRetornadoDoController.email());
        }

        @DisplayName("Busca usuário por id chamando o use case com erro. Usuário não encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId(){
            // Arrange
            UUID id = UUID.randomUUID();

            when(buscarUsuarioPorIdUseCase.buscar(any(UUID.class)))
                    .thenThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado."));

            // Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.buscarUsuarioPorId(id))
                    .isInstanceOf(UsuarioNaoEncontradoException.class)
                    .hasMessage("Não foi encontrado nenhum usuário com o id informado.");
            verify(buscarUsuarioPorIdUseCase, times(1)).buscar(any(UUID.class));
        }
    }

    @Nested
    class BuscarTodosUsuariosRequest {
        @DisplayName("Buscar todos usuarios chamando o use case com sucesso")
        @Test
        void deveRetornarListaDeUsuariosPaginada(){
            // Arrange
            when(buscarTodosUsuariosUseCase.buscar(anyInt()))
                    .thenReturn(new UsuarioPaginacaoCoreDto(new ArrayList<>(), new PaginacaoCoreDto(1, 1, 1)));

            // Act
            var usuariosRetornadosPeloController = usuarioCoreController.buscarTodos(1);

            // Assert
            assertThat(usuariosRetornadosPeloController).isNotNull();
            verify(buscarTodosUsuariosUseCase, times(1)).buscar(anyInt());
        }

        @DisplayName("Buscar todos usuarios com erro. Pagina nao contem nenhum usuario")
        @Test
        void deveRetornarExcecaoSePaginaForMaiorDoQueOLimite(){
            // Arrange
            int pagina = 10;

            when(buscarTodosUsuariosUseCase.buscar(anyInt())).thenThrow(new UsuarioNaoEncontradoException("Não foram encontrados usuários na base de dados para a página informada."));

            // Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.buscarTodos(pagina))
                    .isInstanceOf(UsuarioNaoEncontradoException.class)
                    .hasMessage("Não foram encontrados usuários na base de dados para a página informada.");
            verify(buscarTodosUsuariosUseCase, times(1)).buscar(anyInt());
        }
    }

    @Nested
    class AtualizarEmailRequest {

        @DisplayName("Atualizar email chamando o use case com sucesso")
        @Test
        void devePermitirAtualizarEmail(){
            // Arrange
            UUID id = UUID.randomUUID();
            String novoEmail = "john.doe@email.com";

            doNothing().when(atualizarEmailUsuarioUseCase).atualizar(any(UUID.class), anyString());
            ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> usuarioCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            usuarioCoreController.atualizarEmail(id, novoEmail);

            // Assert
            verify(atualizarEmailUsuarioUseCase, times(1)).atualizar(usuarioCaptor.capture(), emailCaptor.capture());
            assertThat(emailCaptor.getValue()).isEqualTo(novoEmail);
            assertThat(usuarioCaptor.getValue()).isEqualTo(id);
        }

        @DisplayName("Atualizar email chamando o use case com erro. Usuário nao encontrado.")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
            // Arrange
            UUID id = UUID.randomUUID();
            String novoEmail = "john.doe@email.com";

            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(atualizarEmailUsuarioUseCase).atualizar(any(UUID.class), anyString());

            // Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.atualizarEmail(id, novoEmail))
                    .isInstanceOf(UsuarioNaoEncontradoException.class)
                    .hasMessage("Não foi encontrado nenhum usuário com o id informado.");
            verify(atualizarEmailUsuarioUseCase, times(1)).atualizar(any(UUID.class), anyString());
        }

        @DisplayName("Atualizar email chamando o use case com erro. Usuário encontrado inativo")
        @Test
        void deveLancarExcecaoSeNaoEncontrarUsuarioAtivoPorId(){
            // Arrange
            UUID id = UUID.randomUUID();
            String novoEmail = "john.doe@email.com";

            doThrow(new AtualizacaoEmailUsuarioNaoPermitidoException("Não é possível alterar o email de um usuário inativo.")).when(atualizarEmailUsuarioUseCase).atualizar(any(UUID.class), anyString());

            // Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.atualizarEmail(id, novoEmail))
                    .isInstanceOf(AtualizacaoEmailUsuarioNaoPermitidoException.class)
                    .hasMessage("Não é possível alterar o email de um usuário inativo.");
            verify(atualizarEmailUsuarioUseCase, times(1)).atualizar(any(UUID.class), anyString());
        }

        @DisplayName("Atualizar email com erro. Novo email ja cadastrado.")
        @Test
        void deveLancarExecaoSeEmailJaCadastrado(){
            // Arrange
            UUID id = UUID.randomUUID();
            String novoEmail = "john.doe@email.com";

            doThrow(new AtualizacaoEmailUsuarioNaoPermitidoException("Já existe um usuário com o email informado.")).when(atualizarEmailUsuarioUseCase).atualizar(any(UUID.class), anyString());

            // Act & Assert
            assertThatThrownBy(() -> usuarioCoreController.atualizarEmail(id, novoEmail))
                    .isInstanceOf(AtualizacaoEmailUsuarioNaoPermitidoException.class)
                    .hasMessage("Já existe um usuário com o email informado.");
            verify(atualizarEmailUsuarioUseCase, times(1)).atualizar(any(UUID.class), anyString());
        }
    }
//
//    @Nested
//    class AtualizarPerfilRequest {
//        @DisplayName("Atualizar perfil com sucesso.")
//        @Test
//        void devePermitirAtualizarPerfil(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosPerfilDto novoPerfilId = new DadosPerfilDto(2);
//
//            doNothing().when(usuarioCoreController).atualizarPerfil(any(UUID.class), anyInt());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilId)))
//                    .andDo(print())
//                    .andExpect(status().isNoContent());
//            verify(usuarioCoreController, times(1)).atualizarPerfil(any(UUID.class), anyInt());
//        }
//
//        @DisplayName("Atualizar perfil com erro. Usuário nao encontrado por id.")
//        @Test
//        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosPerfilDto novoPerfilId = new DadosPerfilDto(2);
//
//            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).atualizarPerfil(any(UUID.class), anyInt());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilId)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound());
//            verify(usuarioCoreController, times(1)).atualizarPerfil(any(UUID.class), anyInt());
//        }
//
//        @DisplayName("Atualizar perfil com erro. Usuário encontrado esta inativo")
//        @Test
//        void deveLancarExcecaoSeUsuarioEstiverInativo(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosPerfilDto novoPerfilId = new DadosPerfilDto(2);
//
//            doThrow(new UsuarioInativoException("O perfil selecionado é o mesmo que o usuário já possui.")).when(usuarioCoreController).atualizarPerfil(any(UUID.class), anyInt());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilId)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest());
//            verify(usuarioCoreController, times(1)).atualizarPerfil(any(UUID.class), anyInt());
//        }
//
//        @DisplayName("Atualizar perfil com erro. Novo perfil é o mesmo do ja cadastrado.")
//        @Test
//        void deveLancarExcecaoSeNovoPerfilForOMesmoDoRegistrado(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosPerfilDto novoPerfilId = new DadosPerfilDto(1);
//
//            doThrow(new UsuarioInativoException("O perfil selecionado é o mesmo que o usuário já possui.")).when(usuarioCoreController).atualizarPerfil(any(UUID.class), anyInt());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilId)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.mensagem").value("O perfil selecionado é o mesmo que o usuário já possui."));
//            verify(usuarioCoreController, times(1)).atualizarPerfil(any(UUID.class), anyInt());
//        }
//
//        @DisplayName("Atualizar perfil com erro. DTO com dados invalidos.")
//        @Test
//        void deveLancarExcecaoParaCamposInvalidosNoDto(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosPerfilDto novoPerfilIdNulo = new DadosPerfilDto(null);
//            DadosPerfilDto novoPerfilIdNegativo = new DadosPerfilDto(-1);
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilIdNulo)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("O campo idPerfil precisa estar preenchido.")));
//
//            mockMvc.perform(patch("/usuarios/{id}/perfil", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoPerfilIdNegativo)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("O campo idPerfil precisa ter valor maior do que 0.")));
//
//            verify(usuarioCoreController, times(0)).atualizarPerfil(any(UUID.class), anyInt());
//        }
//    }
//
//    @Nested
//    class AtualizarEnderecoRequest {
//        @DisplayName("Atualizar endereco com sucesso.")
//        @Test
//        void devePermitirAtualizarEndereco(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosEnderecoDto novoEndereco = dadosEnderecoDtoValido();
//
//            doNothing().when(usuarioCoreController)
//                    .atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoEndereco)))
//                    .andDo(print())
//                    .andExpect(status().isNoContent());
//            verify(usuarioCoreController, times(1)).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//        }
//
//        @DisplayName("Atualizar endereço com erro. Usuario nao encontrado por id.")
//        @Test
//        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosEnderecoDto novoEndereco = dadosEnderecoDtoValido();
//
//            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoEndereco)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.*").value(hasItem("Não foi encontrado nenhum usuário com o id informado.")));
//            verify(usuarioCoreController, times(1)).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//        }
//
//        @DisplayName("Atualizar endereço com erro. Usuario encontrado esta inativo.")
//        @Test
//        void deveLancarExcecaoSeUsuarioEstiverInativo(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosEnderecoDto novoEndereco = dadosEnderecoDtoValido();
//
//            doThrow(new UsuarioInativoException("Não é possível alterar o nome de um usuário inativo.")).when(usuarioCoreController).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoEndereco)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("Não é possível alterar o nome de um usuário inativo.")));
//            verify(usuarioCoreController, times(1)).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//        }
//
////        @DisplayName("Atualizar endereço com erro. Endereço nao encontrado por id.")
////        @Test
////        void deveLancarExcecaoSeNaoEncontrarEnderecoPorId(){
////            // Arrange
////            UUID id = UUID.randomUUID();
////            DadosEnderecoDto novoEndereco = dadosEnderecoDtoValido();
////
////            doThrow(new Exception("Não foi encontrado nenhum endereço com o id informado.")).when(usuarioCoreController).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
////
////            // Act & Assert
////            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(asJsonString(novoEndereco)))
////                    .andDo(print())
////                    .andExpect(status().isBadRequest())
////                    .andExpect(jsonPath("$.*").value(hasItem("Não foi encontrado nenhum endereço com o id informado.")));
////            verify(usuarioCoreController, times(1)).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
////        }
//
//        @DisplayName("Atualizar endereço com erro. DTO com dados inválidos.")
//        @ParameterizedTest
//        @CsvSource({
//                " , 24455450, Nova Cidade, Rua Aquidabã, Rio de Janeiro, O campo cidade precisa ser informado.",
//                "São Gonçalo, , Nova Cidade, Rua Aquidabã, Rio de Janeiro, O campo cep precisa ser informado.",
//                "São Gonçalo, 24455450, , Rua Aquidabã, Rio de Janeiro, O campo bairro precisa ser informado.",
//                "São Gonçalo, 24455450, Nova Cidade, , Rio de Janeiro, O campo endereco precisa ser informado.",
//                "São Gonçalo, 24455450, Nova Cidade, Rua Aquidabã, , O campo estado precisa ser informado."
//        })
//        void deveLancarExcecaoParaCamposDeEnderecoInvalidos(String cidade, String cep, String bairro, String endereco, String estado, String expectedError){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosEnderecoDto novoEndereco = new DadosEnderecoDto(
//                    cidade,
//                    cep,
//                    bairro,
//                    endereco,
//                    estado,
//                    79,
//                    "Casa 8"
//            );
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/endereco", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoEndereco)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
//            verify(usuarioCoreController, times(0)).atualizarDadosEndereco(any(UUID.class), any(DadosEnderecoDto.class));
//        }
//
//    }
//
//    @Nested
//    class AtualizarNomeRequest {
//        @DisplayName("Atualizar nome com sucesso.")
//        @Test
//        void devePermitirAtualizarNome(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosNomeDto novoNome = new DadosNomeDto("John Doe");
//
//            doNothing().when(usuarioCoreController).atualizarNome(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoNome)))
//                    .andDo(print())
//                    .andExpect(status().isNoContent());
//            verify(usuarioCoreController, times(1)).atualizarNome(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar nome com erro. Usuario nao encontrado por id.")
//        @Test
//        void deveLancarExcecaoSeNaoEncontrarUsuarioPorId(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosNomeDto novoNome = new DadosNomeDto("John Doe");
//
//            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).atualizarNome(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoNome)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.*").value(hasItem("Não foi encontrado nenhum usuário com o id informado.")));
//            verify(usuarioCoreController, times(1)).atualizarNome(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar nome com erro. Usuario encontrado esta inativo")
//        @Test
//        void deveLancarExcecaoSeUsuarioEncontradoEstiverInativo(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosNomeDto novoNome = new DadosNomeDto("John Doe");
//
//            doThrow(new UsuarioInativoException("Não é possível alterar o nome de um usuário inativo.")).when(usuarioCoreController).atualizarNome(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoNome)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("Não é possível alterar o nome de um usuário inativo.")));
//            verify(usuarioCoreController, times(1)).atualizarNome(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar nome com erro. Novo nome igual ao atual.")
//        @Test
//        void deveLancarExcecaoSeNovoNomeForIgualAoAtual(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosNomeDto novoNome = new DadosNomeDto("John Doe");
//
//            doThrow(new AtualizacaoNomeUsuarioNaoPermitidoException("Não é possível alterar o nome do usuário pois ele é igual ao nome do usuário.")).when(usuarioCoreController).atualizarNome(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoNome)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("Não é possível alterar o nome do usuário pois ele é igual ao nome do usuário.")));
//            verify(usuarioCoreController, times(1)).atualizarNome(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar nome com erro. DTO com dados invalidos.")
//        @ParameterizedTest
//        @CsvSource({
//                " , O campo nome precisa estar preenchido.",
//                "Jo, O campo nome precisa ter entre 3 e 150 caracteres."
//        })
//        void deveLancarExcecaoParaCamposInvalidosNoDto(String nome, String expectedError){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            DadosNomeDto novoNome = new DadosNomeDto(nome);
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novoNome)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem(expectedError)));
//            verify(usuarioCoreController, times(0)).atualizarNome(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar nome com erro. Novo nome com mais de 150 caracteres.")
//        @Test
//        void deveLancarExcecaoSeNomeNovoMaiorQue150Characters(){
//            // Arrange
//            UUID id = UUID.randomUUID();
//            String nome = "A".repeat(151);
//            DadosNomeDto nomeRecordRequest = new DadosNomeDto(nome);
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/nome", id)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(nomeRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem("O campo nome precisa ter entre 3 e 150 caracteres.")));
//            verify(usuarioCoreController, times(0)).atualizarNome(any(UUID.class), anyString());
//        }
//    }
//
//    @Nested
//    class AtualizarSenhaRequest {
//        @DisplayName("Trocar senha com sucesso")
//        @Test
//        void devePermitirTrocarSenha(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            SenhaDto novaSenhaDto = new SenhaDto("124");
//
//            doNothing().when(usuarioCoreController).atualizarSenha(any(UUID.class), anyString());
//
//            // Act & Assert
//            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
//            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
//                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
//                                MensagensUtil.SUCESSO_TROCA_SENHA_USUARIO, new Object[0]))
//                        .thenReturn("Senha alterada com sucesso.");
//
//                mockMvc.perform(patch("/usuarios/{id}/senha", usuarioId)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(novaSenhaDto)))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.mensagem").value("Senha alterada com sucesso."));
//            }
//
//            verify(usuarioCoreController, times(1)).atualizarSenha(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar senha com erro. Usuario nao encontrado atraves do id")
//        @Test
//        void deveLancarExcecaoSeUsuarioNaoEncontradoAtravesDoId(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            SenhaDto novaSenhaDto = new SenhaDto("124");
//
//            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).atualizarSenha(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/senha", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaSenhaDto)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o id informado."));
//
//            verify(usuarioCoreController, times(1)).atualizarSenha(any(UUID.class), anyString());
//        }
//
////        @DisplayName("Atualizar senha com erro. Não encontrado usuário vinculado com o login")
////        @Test
////        void deveLancarExcecaoSeNaoEncontrarUsuarioVinculadoComOLogin(){
////            // Arrange
////            String matricula = "us0003";
////            SenhaDto novaSenhaDto = new SenhaDto("124");
////
////            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o login informado.")).when(loginCoreController).atualizarSenha(anyString(), anyString());
////
////            // Act & Arrange
////            mockMvc.perform(patch("/login/{matricula}/senha", matricula)
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(asJsonString(novaSenhaDto)))
////                    .andDo(print())
////                    .andExpect(status().isNotFound())
////                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o login informado."));
////
////            verify(loginCoreController, times(1)).atualizarSenha(anyString(), anyString());
////        }
//
//        @DisplayName("Atualizar senha com erro. Usuário encontrado esta inativo")
//        @Test
//        void deveLancarExcecaoSeUsuarioEstiverInativo(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            SenhaDto novaSenhaDto = new SenhaDto("124");
//
//            doThrow(new UsuarioInativoException("Não é possível alterar a senha de um usuário inativo.")).when(usuarioCoreController).atualizarSenha(any(UUID.class), anyString());
//
//            // Act & Arrange
//            mockMvc.perform(patch("/usuarios/{id}/senha", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaSenhaDto)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.mensagem").value("Não é possível alterar a senha de um usuário inativo."));
//
//            verify(usuarioCoreController, times(1)).atualizarSenha(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Alterar senha com erro. Dados inválidos no DTO")
//        @Test
//        void deveLancarExcecaoParaCamposInvalidos(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            SenhaDto novaSenhaDto = new SenhaDto("");
//
//            // Act & Arrange
//            mockMvc.perform(patch("/usuarios/{id}/senha", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaSenhaDto)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.senha").value("O campo senha precisa estar preenchido."));
//
//            verify(usuarioCoreController, times(0)).atualizarSenha(any(UUID.class), anyString());
//        }
//    }
//
//    @Nested
//    class AtualizarMatriculaRequest {
//        @DisplayName("Trocar matricula com sucesso")
//        @Test
//        void devePermitirTrocarMatricula(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");
//
//            doNothing().when(usuarioCoreController).atualizarMatricula(any(UUID.class), anyString());
//
//            // Act & Assert
//            // Tive que mockar o Mensagem Util nesse teste para poder checar a mensagem
//            try (MockedStatic<MensagensUtil> mensagensMock = mockStatic(MensagensUtil.class)) {
//                mensagensMock.when(() -> MensagensUtil.recuperarMensagem(
//                                MensagensUtil.SUCESSO_TROCA_MATRICULA_USUARIO, new Object[0]))
//                        .thenReturn("Matricula alterada com sucesso.");
//
//                mockMvc.perform(patch("/usuarios/{id}/matricula", usuarioId)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(asJsonString(novaMatriculaDto)))
//                        .andDo(print())
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.mensagem").value("Matricula alterada com sucesso."));
//            }
//            verify(usuarioCoreController, times(1)).atualizarMatricula(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar matricula com erro. Usuário nao encontrado através do id.")
//        @Test
//        void deveLancarExcecaoSeUsuarioNaoEncontradoAtravesDoId(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");
//
//            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o id informado.")).when(usuarioCoreController).atualizarMatricula(any(UUID.class), anyString());
//
//            // Act & Assert
//            mockMvc.perform(patch("/usuarios/{id}/matricula", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaMatriculaDto)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o id informado."));
//
//            verify(usuarioCoreController, times(1)).atualizarMatricula(any(UUID.class), anyString());
//        }
//
////        @DisplayName("Atualizar matricula com erro. Não encontrado usuário vinculado com o login")
////        @Test
////        void deveLancarExcecaoSeNaoEncontrarUsuarioVinculadoComOLogin(){
////            // Arrange
////            String matriculaAtual = "us0008";
////            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");
////
////            doThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o login informado.")).when(loginCoreController).atualizarMatricula(anyString(), anyString());
////
////            // Act & Arrange
////            mockMvc.perform(patch("/login/{matricula}/matricula", matriculaAtual)
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(asJsonString(novaMatriculaDto)))
////                    .andDo(print())
////                    .andExpect(status().isNotFound())
////                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o login informado."));
////
////            verify(loginCoreController, times(1)).atualizarMatricula(anyString(), anyString());
////        }
//
//        @DisplayName("Atualizar matricula com erro. Usuário encontrado está inativo")
//        @Test
//        void deveLancarExcecaoSeUsuarioEstiverInativo(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            MatriculaDto novaMatriculaDto = new MatriculaDto("us0010");
//
//            doThrow(new UsuarioInativoException("Não é possível alterar a senha de um usuário inativo.")).when(usuarioCoreController).atualizarMatricula(any(UUID.class), anyString());
//
//            // Act & Arrange
//            mockMvc.perform(patch("/usuarios/{id}/matricula", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaMatriculaDto)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.mensagem").value("Não é possível alterar a senha de um usuário inativo."));
//
//            verify(usuarioCoreController, times(1)).atualizarMatricula(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Atualizar matricula com erro. Nova matricula ja cadastrada.")
//        @Test
//        void deveLancarExcecaoSeNovaMatriculaJaCadastrada(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            MatriculaDto novaMatriculaDto = new MatriculaDto("us0002");
//
//            doThrow(new MatriculaDuplicadaException("Já existe um usuário com a matrícula informada.")).when(usuarioCoreController).atualizarMatricula(any(UUID.class), anyString());
//
//            // Act & Arrange
//            mockMvc.perform(patch("/usuarios/{id}/matricula", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaMatriculaDto)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.mensagem").value("Já existe um usuário com a matrícula informada."));
//
//            verify(usuarioCoreController, times(1)).atualizarMatricula(any(UUID.class), anyString());
//        }
//
//        @DisplayName("Alterar matricula com erro. Dados inválidos no DTO")
//        @Test
//        void deveLancarExcecaoParaCamposInvalidos(){
//            // Arrange
//            UUID usuarioId = UUID.randomUUID();
//            MatriculaDto novaMatriculaDto = new MatriculaDto("");
//
//            // Act & Arrange
//            mockMvc.perform(patch("/usuarios/{id}/matricula", usuarioId)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(novaMatriculaDto)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.matricula").value("O campo matricula precisa estar preenchido."));
//
//            verify(usuarioCoreController, times(0)).atualizarMatricula(any(UUID.class), anyString());
//        }
//    }
//
//    @Nested
//    class ValidarLoginRequest {
//        @DisplayName("Validação de login com sucesso")
//        @Test
//        void devePermitirValidarLogin(){
//            // Arrange
//            LoginDto loginRecordRequest = loginDtoValido();
//
//            when(usuarioCoreController.validarAcesso(anyString(), anyString()))
//                    .thenReturn("Acesso liberado.");
//
//            // Act & Assert
//            mockMvc.perform(post("/usuarios/valida-acesso")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(loginRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.mensagem").value("Acesso liberado."));
//            verify(usuarioCoreController, times(1)).validarAcesso(anyString(), anyString());
//        }
//
//        @DisplayName("Validação de login com erro. Usuario não encontrado através de matrícula e senha.")
//        @Test
//        void deveLancarExcecaoSeUsuarioNaoEncontradoAtravesDaMatriculaESenha(){
//            // Arrange
//            LoginDto loginRecordRequest = loginDtoValido();
//
//            when(usuarioCoreController.validarAcesso(anyString(), anyString()))
//                    .thenThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com a matrícula e senha informados."));
//
//            // Act & Assert
//            mockMvc.perform(post("/usuarios/valida-acesso")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(loginRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com a matrícula e senha informados."));
//            verify(usuarioCoreController, times(1)).validarAcesso(anyString(), anyString());
//        }
//
//        @DisplayName("Validação de login com erro. Usuario encontrado está inativo")
//        @Test
//        void deveLancarExcecaoSeUsuarioEncontradoEstiverInativo(){
//            // Arrange
//            LoginDto loginRecordRequest = loginDtoValido();
//
//            when(usuarioCoreController.validarAcesso(anyString(), anyString()))
//                    .thenThrow(new UsuarioSemAcessoException("Não é possível realizar o login para usuários inativos."));
//
//            // Act & Assert
//            mockMvc.perform(post("/usuarios/valida-acesso")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(loginRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.mensagem").value("Não é possível realizar o login para usuários inativos."));
//            verify(usuarioCoreController, times(1)).validarAcesso(anyString(), anyString());
//        }
//
////            @DisplayName("Validação de login com erro. Usuário não encontrado através do login id")
////            @Test
////            void deveLancarExcecaoSeNaoEncontrarUsuarioVinculadoComOLogin(){
////                // Arrange
////                LoginDto loginRecordRequest = loginDtoValido();
////
////                when(loginCoreController.validar(anyString(), anyString()))
////                        .thenThrow(new UsuarioNaoEncontradoException("Não foi encontrado nenhum usuário com o login informado."));
////
////                // Act & Assert
////                mockMvc.perform(post("/login")
////                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(asJsonString(loginRecordRequest)))
////                        .andDo(print())
////                        .andExpect(status().isNotFound())
////                        .andExpect(jsonPath("$.mensagem").value("Não foi encontrado nenhum usuário com o login informado."));
////                verify(loginCoreController, times(1)).validar(anyString(), anyString());
////            }
//
//        @DisplayName("Validação de login com erro. Erro com dados no DTO")
//        @ParameterizedTest
//        @CsvSource({
//                " , 123, O campo matricula precisa ser informado.",
//                "us0001, , O campo senha precisa ser informado."
//        })
//        void deveLancarExcecaoParaCamposInvalidos(String matricula, String senha, String expectedErrorMessage ){
//            // Arrange
//            LoginDto loginRecordRequest = new LoginDto(null, matricula, senha);
//
//            // Act & Assert
//            mockMvc.perform(post("/usuarios/valida-acesso")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(loginRecordRequest)))
//                    .andDo(print())
//                    .andExpect(status().isBadRequest())
//                    .andExpect(jsonPath("$.*").value(hasItem(expectedErrorMessage)));
//            verify(usuarioCoreController, times(0)).validarAcesso(anyString(), anyString());
//        }
//    }
}
