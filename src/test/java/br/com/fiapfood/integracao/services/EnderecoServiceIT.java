package br.com.fiapfood.integracao.services;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.repositories.interfaces.IEnderecoRepository;
import br.com.fiapfood.services.interfaces.IEnderecoService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validEnderecoRecordRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EnderecoServiceIT {

    @Autowired
    private IEnderecoRepository enderecoRepository;
    @Autowired
    private IEnderecoService enderecoService;

    @Nested
    class AtualizarEndereco {
        @Test
        void deveAtulizarEnderecoNoBancoDeDados() {
            UUID id = UUID.fromString("de6762a9-e373-4a05-a6bb-d345a759b26f");

            EnderecoEntity enderecoAntigo = enderecoRepository.buscarPorId(id).orElseThrow(() ->
                    new IllegalStateException("Endereço com " + id + " não encontrado para o teste")
            );

            EnderecoRecordRequest enderecoRecordRequest = validEnderecoRecordRequest();

            enderecoService.atualizarEndereco(enderecoAntigo, enderecoRecordRequest);

            EnderecoEntity enderecoAtualizado = enderecoRepository.buscarPorId(id).orElseThrow(() ->
                    new IllegalStateException("Endereço com " + id + " não encontrado para o teste")
            );

            assertThat(enderecoAtualizado.getId()).isNotNull().isEqualTo(id);
            assertThat(enderecoAtualizado.getEndereco()).isEqualTo(enderecoRecordRequest.endereco());
            assertThat(enderecoAtualizado.getCidade()).isEqualTo(enderecoRecordRequest.cidade());
            assertThat(enderecoAtualizado.getBairro()).isEqualTo(enderecoRecordRequest.bairro());
            assertThat(enderecoAtualizado.getEstado()).isEqualTo(enderecoRecordRequest.estado());
            assertThat(enderecoAtualizado.getNumero()).isEqualTo(enderecoRecordRequest.numero());
            assertThat(enderecoAtualizado.getCep()).isEqualTo(enderecoRecordRequest.cep());
            assertThat(enderecoAtualizado.getComplemento()).isEqualTo(enderecoRecordRequest.complemento());
            assertThat(enderecoAtualizado.getSemNumero()).isEqualTo(enderecoRecordRequest.semNumero());
        }
    }
}
