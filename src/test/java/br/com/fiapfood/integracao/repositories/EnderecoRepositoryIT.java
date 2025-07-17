package br.com.fiapfood.integracao.repositories;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.repositories.impl.EnderecoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validInexistenteEnderecoEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EnderecoRepositoryIT {

    @Autowired
    private EnderecoRepository enderecoRepository;

    private EnderecoEntity enderecoEntity;

    @BeforeEach
    void setUp() {
        enderecoEntity = validInexistenteEnderecoEntity();
    }

    @Test
    void deveBuscarEnderecoPorId() {
        // Arrange
        UUID enderecoId = UUID.fromString("de6762a9-e373-4a05-a6bb-d345a759b26f");

        // Act
        Optional<EnderecoEntity> enderecoEncontrado = enderecoRepository.buscarPorId(enderecoId);

        // Assert
        assertThat(enderecoEncontrado)
                .isPresent()
                .get()
                .satisfies(endereco -> {
                    assertThat(endereco.getId()).isEqualTo(enderecoId);
                    assertThat(endereco.getEndereco()).isNotNull();
                    assertThat(endereco.getCidade()).isNotNull();
                    assertThat(endereco.getEstado()).isNotNull();
                    assertThat(endereco.getCep()).isNotNull();
                    assertThat(endereco.getNumero()).isNotNull();
                    assertThat(endereco.getBairro()).isNotNull();
                    assertThat(endereco.getComplemento()).isNotNull();
                    assertThat(endereco.getSemNumero()).isNotNull();
                });
    }

    @Test
    void deveSalvarEndereco() {
        // Act
        enderecoRepository.salvar(enderecoEntity);

        // Assert
        Optional<EnderecoEntity> enderecoSalvo = enderecoRepository.buscarPorId(enderecoEntity.getId());
        assertThat(enderecoSalvo)
                .isPresent()
                .get()
                .satisfies(endereco -> {
                    assertThat(endereco.getId()).isEqualTo(enderecoEntity.getId());
                    assertThat(endereco.getEndereco()).isEqualTo(enderecoEntity.getEndereco());
                    assertThat(endereco.getCidade()).isEqualTo(enderecoEntity.getCidade());
                    assertThat(endereco.getEstado()).isEqualTo(enderecoEntity.getEstado());
                    assertThat(endereco.getCep()).isEqualTo(enderecoEntity.getCep());
                    assertThat(endereco.getNumero()).isEqualTo(enderecoEntity.getNumero());
                    assertThat(endereco.getBairro()).isEqualTo(enderecoEntity.getBairro());
                    assertThat(endereco.getComplemento()).isEqualTo(enderecoEntity.getComplemento());
                    assertThat(endereco.getSemNumero()).isEqualTo(enderecoEntity.getSemNumero());
                });
    }

    @Test
    void deveSalvarEnderecoComCamposOpcionaisNulos() {
        // Arrange
        enderecoEntity.setNumero(null);
        enderecoEntity.setComplemento(null);
        enderecoEntity.setSemNumero(true);

        // Act
        enderecoRepository.salvar(enderecoEntity);

        // Assert
        assertThat(enderecoEntity.getId()).isNotNull();

        Optional<EnderecoEntity> enderecoSalvo = enderecoRepository.buscarPorId(enderecoEntity.getId());
        assertThat(enderecoSalvo)
                .isPresent()
                .get()
                .satisfies(endereco -> {
                    assertThat(endereco.getId()).isEqualTo(enderecoEntity.getId());
                    assertThat(endereco.getEndereco()).isEqualTo(enderecoEntity.getEndereco());
                    assertThat(endereco.getCidade()).isEqualTo(enderecoEntity.getCidade());
                    assertThat(endereco.getEstado()).isEqualTo(enderecoEntity.getEstado());
                    assertThat(endereco.getCep()).isEqualTo(enderecoEntity.getCep());
                    assertThat(endereco.getBairro()).isEqualTo(enderecoEntity.getBairro());
                    assertThat(endereco.getNumero()).isNull();
                    assertThat(endereco.getComplemento()).isNull();
                    assertThat(endereco.getSemNumero()).isTrue();
                });
    }

    @Test
    void deveAtualizarEnderecoExistenteComSucesso() {
        // Arrange
        UUID enderecoIdExistente = UUID.fromString("229f4bff-c6be-4008-8911-bef0c79735e2");

        Optional<EnderecoEntity> enderecoExistente = enderecoRepository.buscarPorId(enderecoIdExistente);
        assertThat(enderecoExistente).isPresent();

        EnderecoEntity enderecoParaAtualizar = enderecoExistente.get();
        enderecoParaAtualizar.setEndereco("Endereco Atualizado");
        enderecoParaAtualizar.setCidade("Cidade Atualizado");
        enderecoParaAtualizar.setNumero(999);
        enderecoParaAtualizar.setBairro("Bairro Atualizado");
        enderecoParaAtualizar.setComplemento("Complemento Atualizado");

        // Act
        enderecoRepository.salvar(enderecoParaAtualizar);

        // Assert
        Optional<EnderecoEntity> enderecoAtualizado = enderecoRepository.buscarPorId(enderecoIdExistente);

        assertThat(enderecoAtualizado)
                .isPresent()
                .get()
                .satisfies(endereco -> {
                    assertThat(endereco.getId()).isEqualTo(enderecoIdExistente);
                    assertThat(endereco.getEndereco()).isEqualTo("Endereco Atualizado");
                    assertThat(endereco.getCidade()).isEqualTo("Cidade Atualizado");
                    assertThat(endereco.getNumero()).isEqualTo(999);
                    assertThat(endereco.getBairro()).isEqualTo("Bairro Atualizado");
                    assertThat(endereco.getComplemento()).isEqualTo("Complemento Atualizado");
                });
    }
}
