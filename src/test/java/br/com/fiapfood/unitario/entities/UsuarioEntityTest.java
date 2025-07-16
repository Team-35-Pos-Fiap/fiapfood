package br.com.fiapfood.unitario.entities;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiapfood.utils.DataGenerator.validPerfilEntity;
import static br.com.fiapfood.utils.DataGenerator.validEnderecoEntity;
import static br.com.fiapfood.utils.DataGenerator.validLoginEntity;

import static org.assertj.core.api.Assertions.assertThat;
public class UsuarioEntityTest {

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntity(
                UUID.randomUUID(),
                "Nome Teste",
                "teste@email.com",
                LocalDateTime.now().minusDays(1),
                null,
                true,
                validEnderecoEntity(),
                validPerfilEntity(),
                validLoginEntity()
        );
    }

    private void assertDataAtualizacao(UsuarioEntity usuario, LocalDateTime dataAntes) {
        assertThat(usuario.getDataAtualizacao())
                .isNotNull()
                .isAfterOrEqualTo(dataAntes)
                .isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void deveAtualizarIsAtivoParaFalse(){
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();

        // Act
        usuario.inativar();

        // Assert
        assertThat(usuario.getIsAtivo()).isFalse();
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarIsAtivoParaTrue(){
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        usuario.inativar();

        // Act
        usuario.reativar();

        // Assert
        assertThat(usuario.getIsAtivo()).isTrue();
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarPerfil() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        PerfilEntity novoPerfil = new PerfilEntity();

        // Act
        usuario.atualizarPerfil(novoPerfil);

        // Assert
        assertThat(usuario.getPerfil()).isEqualTo(novoPerfil);
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarEmail() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        String novoEmail = "novo@email.com";

        // Act
        usuario.atualizarEmail(novoEmail);

        // Assert
        assertThat(usuario.getEmail()).isEqualTo(novoEmail);
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarEndereco() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        String novoEndereco = "Nova Rua";
        String novaCidade = "Nova Cidade";
        String novoBairro = "Novo Bairro";
        String novoEstado = "Novo Estado";
        Integer novoNumero = 1234;
        String novoCep = "12345-678";
        String novoComplemento = "Novo Complemento";
        Boolean semNumero = false;

        // Act
        usuario.atualizarEndereco(
                novoEndereco,
                novaCidade,
                novoBairro,
                novoEstado,
                novoNumero,
                novoCep,
                novoComplemento,
                semNumero
        );

        // Assert
        assertThat(usuario.getDadosEndereco()).isNotNull();
        assertThat(usuario.getDadosEndereco().getEndereco()).isEqualTo(novoEndereco);
        assertThat(usuario.getDadosEndereco().getCidade()).isEqualTo(novaCidade);
        assertThat(usuario.getDadosEndereco().getBairro()).isEqualTo(novoBairro);
        assertThat(usuario.getDadosEndereco().getEstado()).isEqualTo(novoEstado);
        assertThat(usuario.getDadosEndereco().getNumero()).isEqualTo(novoNumero);
        assertThat(usuario.getDadosEndereco().getCep()).isEqualTo(novoCep);
        assertThat(usuario.getDadosEndereco().getComplemento()).isEqualTo(novoComplemento);
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarLogin() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        String novaMatricula = "987654321";
        String novaSenha = "novaSenha123";

        // Act
        usuario.atualizarLogin(novaMatricula, novaSenha);

        // Assert
        assertThat(usuario.getDadosLogin()).isNotNull();
        assertThat(usuario.getDadosLogin().getMatricula()).isEqualTo(novaMatricula);
        assertThat(usuario.getDadosLogin().getSenha()).isEqualTo(novaSenha);
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarNome() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();
        String nome = "novoNome";

        // Act
        usuario.atualizarNome(nome);

        // Assert
        assertThat(usuario.getNome()).isNotNull();
        assertThat(usuario.getNome()).isEqualTo(nome);
        assertDataAtualizacao(usuario, dataAntes);
    }

    @Test
    void deveAtualizarDataAtualizacao() {
        // Arrange
        LocalDateTime dataAntes = LocalDateTime.now();

        // Act
        usuario.atualizarNome("Teste");

        // Assert
        assertDataAtualizacao(usuario, dataAntes);
    }
}

