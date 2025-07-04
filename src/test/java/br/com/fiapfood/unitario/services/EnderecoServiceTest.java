package br.com.fiapfood.unitario.services;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;
import br.com.fiapfood.services.EnderecoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiapfood.utils.DataGenerator.validEnderecoEntity;
import static br.com.fiapfood.utils.DataGenerator.validEnderecoRecordRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnderecoServiceTest {

    @Mock
    private IEnderecoRepository enderecoRepository;
    private EnderecoService enderecoService;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        enderecoService = new EnderecoService(enderecoRepository);
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }

    @Nested
    class TratarDadosEndereco {
        @Test
        void deveAtualizarEnderecoChamandoSalvarNoRepository(){
            EnderecoEntity dadosEnderecoAntigo = validEnderecoEntity();
            EnderecoEntity dadosEnderecoNovos = validEnderecoEntity();
            dadosEnderecoNovos.setEndereco("Endereco Novo");

            doNothing().when(enderecoRepository).salvar(any(EnderecoEntity.class));

            enderecoService.atualizarEndereco(dadosEnderecoAntigo, validEnderecoRecordRequest());

            verify(enderecoRepository, times(1)).salvar(any(EnderecoEntity.class));
        }

        @Test
        void deveAtualizarCamposDoEnderecoCorretamente(){
            EnderecoEntity dadosEnderecoAntigo = validEnderecoEntity();

            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            // mock salvar as void
            doNothing().when(enderecoRepository).salvar(any(EnderecoEntity.class));

            // call
            enderecoService.atualizarEndereco(dadosEnderecoAntigo, enderecoRecordRequest);

            // capture what was sent to salvar
            ArgumentCaptor<EnderecoEntity> captor = ArgumentCaptor.forClass(EnderecoEntity.class);
            verify(enderecoRepository).salvar(captor.capture());

            EnderecoEntity capturado = captor.getValue();

            // assert on captured data
            assertThat(capturado.getEndereco()).isEqualTo(enderecoRecordRequest.endereco());
            assertThat(capturado.getNumero()).isEqualTo(enderecoRecordRequest.numero());
            assertThat(capturado.getCep()).isEqualTo(enderecoRecordRequest.cep());
            assertThat(capturado.getCidade()).isEqualTo(enderecoRecordRequest.cidade());
        }
    }
}
