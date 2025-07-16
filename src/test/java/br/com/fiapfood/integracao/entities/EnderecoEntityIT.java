package br.com.fiapfood.integracao.entities;
import br.com.fiapfood.entities.db.EnderecoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EnderecoEntityIT {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void deveCadastrarEnderecoComSucesso() {
        // Arrange
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setEndereco("Rua das Palmeiras");
        endereco.setCidade("Rio de Janeiro");
        endereco.setBairro("Copacabana");
        endereco.setEstado("RJ");
        endereco.setNumero(100);
        endereco.setCep("22070-900");
        endereco.setComplemento("Apto 201");
        endereco.setSemNumero(false);

        // Act
        EnderecoEntity enderecoSalvo = entityManager.persistAndFlush(endereco);

        // Assert
        assertThat(enderecoSalvo).isNotNull();
        assertThat(enderecoSalvo.getId()).isNotNull();
        assertThat(enderecoSalvo.getEndereco()).isEqualTo("Rua das Palmeiras");
        assertThat(enderecoSalvo.getCidade()).isEqualTo("Rio de Janeiro");
        assertThat(enderecoSalvo.getBairro()).isEqualTo("Copacabana");
        assertThat(enderecoSalvo.getEstado()).isEqualTo("RJ");
        assertThat(enderecoSalvo.getNumero()).isEqualTo(100);
        assertThat(enderecoSalvo.getCep()).isEqualTo("22070-900");
        assertThat(enderecoSalvo.getComplemento()).isEqualTo("Apto 201");
        assertThat(enderecoSalvo.getSemNumero()).isFalse();
    }

    @Test
    void deveAtualizarCamposComMetodoAtualizarDados() {
        // Arrange
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setEndereco("Endereço Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setBairro("Bairro Teste");
        endereco.setEstado("SP");
        endereco.setNumero(50);
        endereco.setCep("01000-000");
        endereco.setComplemento("Casa");
        endereco.setSemNumero(false);

        EnderecoEntity enderecoSalvo = entityManager.persistAndFlush(endereco);
        entityManager.clear(); // Limpa o contexto para forçar busca no banco

        // Act
        EnderecoEntity enderecoEncontrado = entityManager.find(EnderecoEntity.class, enderecoSalvo.getId());
        enderecoEncontrado.atualizarDados(
                "Avenida Paulista",
                "São Paulo",
                "Bela Vista",
                "SP",
                1000,
                "01310-100",
                "Conjunto 15",
                false
        );

        EnderecoEntity enderecoAtualizado = entityManager.persistAndFlush(enderecoEncontrado);

        // Assert
        assertThat(enderecoAtualizado.getId()).isEqualTo(enderecoSalvo.getId());
        assertThat(enderecoAtualizado.getEndereco()).isEqualTo("Avenida Paulista");
        assertThat(enderecoAtualizado.getCidade()).isEqualTo("São Paulo");
        assertThat(enderecoAtualizado.getBairro()).isEqualTo("Bela Vista");
        assertThat(enderecoAtualizado.getEstado()).isEqualTo("SP");
        assertThat(enderecoAtualizado.getNumero()).isEqualTo(1000);
        assertThat(enderecoAtualizado.getCep()).isEqualTo("01310-100");
        assertThat(enderecoAtualizado.getComplemento()).isEqualTo("Conjunto 15");
        assertThat(enderecoAtualizado.getSemNumero()).isFalse();
    }

    @Test
    void devePermitirCadastrarSemNumeroEComplemento() {
        // Arrange
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setEndereco("Travessa da Saudade");
        endereco.setCidade("Recife");
        endereco.setBairro("Boa Viagem");
        endereco.setEstado("PE");
        endereco.setNumero(null); // Sem número
        endereco.setComplemento(null); // Sem complemento
        endereco.setCep("51020-280");
        endereco.setSemNumero(true);

        // Act
        EnderecoEntity enderecoSalvo = entityManager.persistAndFlush(endereco);

        // Assert
        assertThat(enderecoSalvo).isNotNull();
        assertThat(enderecoSalvo.getId()).isNotNull();
        assertThat(enderecoSalvo.getEndereco()).isEqualTo("Travessa da Saudade");
        assertThat(enderecoSalvo.getCidade()).isEqualTo("Recife");
        assertThat(enderecoSalvo.getBairro()).isEqualTo("Boa Viagem");
        assertThat(enderecoSalvo.getEstado()).isEqualTo("PE");
        assertThat(enderecoSalvo.getCep()).isEqualTo("51020-280");
        assertThat(enderecoSalvo.getNumero()).isNull();
        assertThat(enderecoSalvo.getComplemento()).isNull();
        assertThat(enderecoSalvo.getSemNumero()).isTrue();

        entityManager.clear();
        EnderecoEntity enderecoRecuperado = entityManager.find(EnderecoEntity.class, enderecoSalvo.getId());

        assertThat(enderecoRecuperado.getNumero()).isNull();
        assertThat(enderecoRecuperado.getComplemento()).isNull();
    }

    @Test
    void deveGerarIdAutomaticamente() {
        // Arrange
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setEndereco("Rua A");
        endereco.setCidade("Cidade A");
        endereco.setBairro("Bairro A");
        endereco.setEstado("RJ");
        endereco.setCep("20000-000");
        endereco.setSemNumero(false);

        assertThat(endereco.getId()).isNull();

        // Act
        EnderecoEntity enderecoSalvo = entityManager.persistAndFlush(endereco);

        // Assert
        assertThat(enderecoSalvo.getId()).isNotNull();
        assertThat(enderecoSalvo.getId()).isInstanceOf(UUID.class);

        entityManager.clear();
        EnderecoEntity enderecoRecuperado = entityManager.find(EnderecoEntity.class, enderecoSalvo.getId());

        assertThat(enderecoRecuperado).isNotNull();
        assertThat(enderecoRecuperado.getId()).isNotNull();
    }


}
