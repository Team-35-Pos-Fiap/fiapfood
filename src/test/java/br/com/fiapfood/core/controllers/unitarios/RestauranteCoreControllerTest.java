package br.com.fiapfood.core.controllers.unitarios;

import br.com.fiapfood.core.controllers.impl.RestauranteCoreController;
import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.atendimento.AtendimentoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.DadosEnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.restaurante.CadastrarRestauranteCoreDto;
import br.com.fiapfood.core.entities.dto.restaurante.DadosRestauranteCoreDto;
import br.com.fiapfood.core.entities.dto.tipo_culinaria.TipoCulinariaCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioResumidoCoreDto;
import br.com.fiapfood.core.usecases.atendimento.interfaces.*;
import br.com.fiapfood.core.usecases.item.interfaces.*;
import br.com.fiapfood.core.usecases.restaurante.interfaces.*;
import br.com.fiapfood.infraestructure.controllers.request.atendimento.AtendimentoDto;
import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static br.com.fiapfood.utils.DtoDataGenerator.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyInt;

class RestauranteCoreControllerTest {

    @Mock
    private IRestauranteCoreController restauranteCoreController;

    @Mock
    private IBuscarRestaurantePorId buscarRestaurantePorId;

    @Mock
    private IBuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase;

    @Mock
    private ICadastrarRestauranteUseCase cadastrarRestauranteUseCase;

    @Mock
    private IReativarRestauranteUseCase reativarRestauranteUseCase;

    @Mock
    private IInativarRestauranteUseCase inativarRestauranteUseCase;

    @Mock
    private IAtualizarDonoRestauranteUseCase atualizarDonoRestauranteUseCase;

    @Mock
    private IAtualizarEnderecoRestauranteUseCase atualizarEnderecoRestauranteUseCase;

    @Mock
    private IAtualizarNomeRestauranteUseCase atualizarNomeRestauranteUseCase;

    @Mock
    private IAtualizarTipoCulinariaRestauranteUseCase atualizarTipoCulinariaRestauranteUseCase;

    @Mock
    private IAtualizarAtendimentoUseCase atualizarAtendimentoUseCase;

    @Mock
    private IAdicionarAtendimentoUseCase adicionarAtendimentoUseCase;

    @Mock
    private IExcluirAtendimentoUseCase excluirAtendimentoUseCase;

    @Mock
    private IAtualizarDescricaoItemUseCase atualizarDescricaoItemUseCase;

    @Mock
    private IAtualizarNomeItemUseCase atualizarNomeItemUseCase;

    @Mock
    private IAtualizarDisponibilidadeConsumoPresencialItemUseCase atualizarDisponibilidadeConsumoPresencialItemUseCase;

    @Mock
    private IAtualizarDisponibilidadeItemUseCase atualizarDisponibilidadeItemUseCase;

    @Mock
    private IAtualizarImagemItemUseCase atualizarImagemItemUseCase;

    @Mock
    private IAtualizarPrecoItemUseCase atualizarPrecoItemUseCase;

    @Mock
    private IBaixarImagemItemUseCase baixarImagemItemUseCase;

    @Mock
    private IBuscarItemPorIdUseCase buscarItemPorIdUseCase;

    @Mock
    private IBuscarTodosItensUseCase buscarTodosItensUseCase;

    @Mock
    private ICadastrarItemUseCase cadastrarItemUseCase;


    @Mock
    private DadosRestauranteCoreDto restauranteDto;

    @Mock
    private EnderecoCoreDto enderecoDto;

    @Mock
    private DadosUsuarioResumidoCoreDto donoRestauranteDto;

    @Mock
    private TipoCulinariaCoreDto tipoCulinariaCoreDto;

    @Mock
    private AtendimentoCoreDto atendimentoCoreDto;


    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        restauranteCoreController = new RestauranteCoreController(
                buscarRestaurantePorId,
                buscarTodosRestaurantesUseCase,
                cadastrarRestauranteUseCase,
                reativarRestauranteUseCase,
                inativarRestauranteUseCase,
                atualizarDonoRestauranteUseCase,
                atualizarEnderecoRestauranteUseCase,
                atualizarNomeRestauranteUseCase,
                atualizarTipoCulinariaRestauranteUseCase,
                atualizarAtendimentoUseCase,
                adicionarAtendimentoUseCase,
                excluirAtendimentoUseCase,
                atualizarDescricaoItemUseCase,
                atualizarNomeItemUseCase,
                atualizarDisponibilidadeConsumoPresencialItemUseCase,
                atualizarDisponibilidadeItemUseCase,
                atualizarImagemItemUseCase,
                atualizarPrecoItemUseCase,
                baixarImagemItemUseCase,
                buscarItemPorIdUseCase,
                buscarTodosItensUseCase,
                cadastrarItemUseCase
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class GerenciarRestauranteRequest {

        @Test
        @DisplayName("Deve buscar restaurantes com paginação")
        void deveBuscarRestaurantesComSucesso() throws Exception {
            // Arrange and Act
            when(buscarTodosRestaurantesUseCase.buscar(anyInt())).thenReturn(restaurantePaginacaoCoreDtoValido());
            var restaurantesRetornadosPeloController = restauranteCoreController.buscarTodos(1);

            // Assert
            assertThat(restaurantesRetornadosPeloController).isNotNull();
            verify(buscarTodosRestaurantesUseCase, times(1)).buscar(anyInt());
        }

        @Test
        @DisplayName("Deve buscar restaurante por id")
        void deveBuscarRestaurantePorIdComSucesso() throws Exception {
            // Arrange
            when(buscarRestaurantePorId.buscar(any(UUID.class)))
                    .thenReturn(dadosRestauranteCoreDtoValido());
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            var usuarioRetornadoDoController = restauranteCoreController.buscarPorId(dadosRestauranteCoreDtoValido().id());

            // Assert
            verify(buscarRestaurantePorId, times(1)).buscar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(dadosRestauranteCoreDtoValido().id());
            assertThat(dadosRestauranteCoreDtoValido().id()).isEqualTo(usuarioRetornadoDoController.id());
        }

        @Test
        @DisplayName("Deve cadastrar restaurante")
        void deveCadastrarRestauranteComSucesso() throws Exception {
            // Arrange
            CadastrarRestauranteDto cadastrarRestauranteDto = cadastrarRestauranteDtoValido();
            CadastrarRestauranteCoreDto cadastrarRestauranteCoreDto = cadastrarRestauranteCoreDtoValido();

            doNothing().when(cadastrarRestauranteUseCase).cadastrar(any(CadastrarRestauranteCoreDto.class));
            ArgumentCaptor<CadastrarRestauranteCoreDto> captor = ArgumentCaptor.forClass(CadastrarRestauranteCoreDto.class);

            // Act
            restauranteCoreController.cadastrar(cadastrarRestauranteDto);

            // Assert
            verify(cadastrarRestauranteUseCase, times(1)).cadastrar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(cadastrarRestauranteCoreDto);
        }

        @Test
        @DisplayName("Deve inativar restaurante")
        void deveInativarRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(inativarRestauranteUseCase).inativar(any(UUID.class));
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.inativar(id);

            // Assert
            verify(inativarRestauranteUseCase, times(1)).inativar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve reativar restaurante")
        void deveReativarRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();

            doNothing().when(reativarRestauranteUseCase).reativar(any(UUID.class));
            ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.reativar(id);

            // Assert
            verify(reativarRestauranteUseCase, times(1)).reativar(captor.capture());
            assertThat(captor.getValue()).isEqualTo(id);

        }

        @Test
        @DisplayName("Deve atualizar TipoCulinaria restaurante")
        void deveAtualizarTipoCulinariaRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            int novoTipo = 2;

            doNothing().when(atualizarTipoCulinariaRestauranteUseCase).atualizar(any(UUID.class), anyInt());
            ArgumentCaptor<Integer> tipoCulinariaIdCaptor = ArgumentCaptor.forClass(Integer.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.atualizarTipoCulinaria(id, novoTipo);

            // Assert
            verify(atualizarTipoCulinariaRestauranteUseCase, times(1)).atualizar(restauranteIdCaptor.capture(), tipoCulinariaIdCaptor.capture());
            assertThat(tipoCulinariaIdCaptor.getValue()).isEqualTo(novoTipo);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve atualizar dono do restaurante")
        void deveAtualizarDonoRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            UUID idDono = UUID.randomUUID();

            doNothing().when(atualizarDonoRestauranteUseCase).atualizar(any(UUID.class), any(UUID.class));
            ArgumentCaptor<UUID> donoIdCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.atualizarDono(id, idDono);

            // Assert
            verify(atualizarDonoRestauranteUseCase, times(1)).atualizar(restauranteIdCaptor.capture(), donoIdCaptor.capture());
            assertThat(donoIdCaptor.getValue()).isEqualTo(idDono);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve atualizar nome do restaurante")
        void deveAtualizarNomeRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            String novoNome = "Novo Restaurante";

            doNothing().when(atualizarNomeRestauranteUseCase).atualizar(any(UUID.class), anyString());
            ArgumentCaptor<String> nomeCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.atualizarNome(id, novoNome);

            // Assert
            verify(atualizarNomeRestauranteUseCase, times(1)).atualizar(restauranteIdCaptor.capture(), nomeCaptor.capture());
            assertThat(nomeCaptor.getValue()).isEqualTo(novoNome);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve atualizar endereco do restaurante")
        void deveAtualizarEnderecoRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            DadosEnderecoDto dadosEndereco = dadosEnderecoDtoValido();
            DadosEnderecoCoreDto dadosEnderecoCore = dadosEnderecoCoreDtoValido();

            doNothing().when(atualizarEnderecoRestauranteUseCase).atualizar(any(UUID.class), any(DadosEnderecoCoreDto.class));
            ArgumentCaptor<DadosEnderecoCoreDto> enderecoCaptor = ArgumentCaptor.forClass(DadosEnderecoCoreDto.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.atualizarEndereco(id, dadosEndereco);

            // Assert
            verify(atualizarEnderecoRestauranteUseCase, times(1)).atualizar(restauranteIdCaptor.capture(), enderecoCaptor.capture());
            assertThat(enderecoCaptor.getValue()).isEqualTo(dadosEnderecoCore);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve atualizar atendimento do restaurante")
        void deveAtualizarAtendimentoRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            AtendimentoDto atendimento = atendimentoDtoValido();
            AtendimentoCoreDto atendimentoCore = atendimentoCoreDtoValido();

            doNothing().when(atualizarAtendimentoUseCase).atualizar(any(UUID.class), any(AtendimentoCoreDto.class));
            ArgumentCaptor<AtendimentoCoreDto> atendimentoCaptor = ArgumentCaptor.forClass(AtendimentoCoreDto.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.atualizarAtendimento(id, atendimento);

            // Assert
            verify(atualizarAtendimentoUseCase, times(1)).atualizar(restauranteIdCaptor.capture(), atendimentoCaptor.capture());
            assertThat(atendimentoCaptor.getValue()).isEqualTo(atendimentoCore);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve adicionar Atendimento do restaurante")
        void deveAdicionarAtendimentoRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            AtendimentoDto atendimento = atendimentoDtoValido();
            AtendimentoCoreDto atendimentoCore = atendimentoCoreDtoValido();

            doNothing().when(adicionarAtendimentoUseCase).adicionar(any(UUID.class), any(AtendimentoCoreDto.class));
            ArgumentCaptor<AtendimentoCoreDto> atendimentoCaptor = ArgumentCaptor.forClass(AtendimentoCoreDto.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.adicionarAtendimento(id, atendimento);

            // Assert
            verify(adicionarAtendimentoUseCase, times(1)).adicionar(restauranteIdCaptor.capture(), atendimentoCaptor.capture());
            assertThat(atendimentoCaptor.getValue()).isEqualTo(atendimentoCore);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve excluir Atendimento do restaurante")
        void deveExcluirAtendimentoRestauranteComSucesso() throws Exception {
            // Arrange
            UUID id = UUID.randomUUID();
            UUID idAtendimento = UUID.randomUUID();

            doNothing().when(excluirAtendimentoUseCase).excluir(any(UUID.class), any(UUID.class));
            ArgumentCaptor<UUID> idAtendimentoCaptor = ArgumentCaptor.forClass(UUID.class);
            ArgumentCaptor<UUID> restauranteIdCaptor = ArgumentCaptor.forClass(UUID.class);

            // Act
            restauranteCoreController.excluirAtendimento(id, idAtendimento);

            // Assert
            verify(excluirAtendimentoUseCase, times(1)).excluir(restauranteIdCaptor.capture(), idAtendimentoCaptor.capture());
            assertThat(idAtendimentoCaptor.getValue()).isEqualTo(idAtendimento);
            assertThat(restauranteIdCaptor.getValue()).isEqualTo(id);
        }

    }

    @Nested
    class GerenciarItemRequest {

        @DisplayName("Buscar todos os itens cadastrados")
        @Test
        void deveRetornarListaComItensCadastradosComSucesso() throws Exception {

        }

        @DisplayName("Buscar item por id com sucesso")
        @Test
        void deveRetornarItemPorIdComSucesso() throws Exception {

        }

        @DisplayName("Buscar item por id com erro. Item nao encontrado através do id")
        @Test
        void deveLancarExcecaoSeNaoEncontrarItemPorId() throws Exception {

        }

        @DisplayName("Deve cadastrar um novo item com sucesso")
        @Test
        void deveCadastrarItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar a descricao do item com sucesso")
        void deveAtualizarDescricaoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o nome do item com sucesso")
        void deveAtualizarNomeItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o preco do item com sucesso")
        void deveAtualizarPrecoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar o disponibilidadeConsumoPresencial do item com sucesso")
        void deveAtualizarDisponibilidadeConsumoPresencialItemComSucesso() throws Exception {

        }


        @Test
        @DisplayName("Deve atualizar o disponibilidade do item com sucesso")
        void deveAtualizarDisponibilidadeComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve atualizar a imagem do item com sucesso")
        void deveAtualizarImagemDoItemComSucesso() throws Exception {

        }

        @Test
        @DisplayName("Deve baixar a imagem do item com sucesso")
        void deveBaixarImagemDoItemComSucesso() throws Exception {

        }

    }
}
