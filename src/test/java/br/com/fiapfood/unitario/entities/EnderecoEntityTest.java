package br.com.fiapfood.unitario.entities;

import br.com.fiapfood.entities.db.EnderecoEntity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EnderecoEntityTest {

    @Test
    void deveAtualizarOsDados() {
        // Arrange
        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setId(UUID.randomUUID());

        String novoEndereco = "Rua das Flores";
        String novaCidade = "São Paulo";
        String novoBairro = "Vila Madalena";
        String novoEstado = "SP";
        Integer novoNumero = 1234;
        String novoCep = "01234-567";
        String novoComplemento = "Apto 45";
        Boolean novoSemNumero = false;

        // Act
        endereco.atualizarDados(
                novoEndereco,
                novaCidade,
                novoBairro,
                novoEstado,
                novoNumero,
                novoCep,
                novoComplemento,
                novoSemNumero
        );

        // Assertion
        assertThat(endereco.getEndereco()).isEqualTo(novoEndereco);
        assertThat(endereco.getCidade()).isEqualTo(novaCidade);
        assertThat(endereco.getBairro()).isEqualTo(novoBairro);
        assertThat(endereco.getEstado()).isEqualTo(novoEstado);
        assertThat(endereco.getNumero()).isEqualTo(novoNumero);
        assertThat(endereco.getCep()).isEqualTo(novoCep);
        assertThat(endereco.getComplemento()).isEqualTo(novoComplemento);
        assertThat(endereco.getSemNumero()).isEqualTo(novoSemNumero);
    }
}
