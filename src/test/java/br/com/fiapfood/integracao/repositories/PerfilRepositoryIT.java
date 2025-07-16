package br.com.fiapfood.integracao.repositories;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.repositories.exceptions.PerfilNaoEncontradoException;
import br.com.fiapfood.repositories.impl.PerfilRepository;
import br.com.fiapfood.repositories.interfaces.jpa.IPerfilJpaRepository;
import br.com.fiapfood.utils.MensagensUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PerfilRepositoryIT {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private IPerfilJpaRepository perfilJpaRepository;

    @Test
    void deveBuscarPorId() {
        // Arrange
        PerfilEntity perfilCliente = criaPerfilCliente();
        PerfilEntity perfilSalvo = perfilJpaRepository.save(perfilCliente);
        Integer idBusca = perfilSalvo.getId();

        // Act
        PerfilEntity perfilEncontrado = perfilRepository.buscarPorId(idBusca);

        // Assert
        assertThat(perfilEncontrado).isNotNull();
        assertThat(perfilEncontrado.getId()).isEqualTo(idBusca);
        assertThat(perfilEncontrado.getNome()).isEqualTo(perfilCliente.getNome());
    }

    @Test
    void deveLancarExceptionAoBuscarPorIdInexistente() {
        // Arrange
        Integer idInexistente = 9999;

        // Act + Assert
        assertThatThrownBy(() -> perfilRepository.buscarPorId(idInexistente))
                .isInstanceOf(PerfilNaoEncontradoException.class)
                .hasMessageContaining(MensagensUtil.recuperarMensagem(MensagensUtil.ERRO_PERFIL_NAO_ENCONTRADO));
    }

    @Test
    void deveBuscarTodosPerfisCadastrados() {
        // Arrange
        PerfilEntity perfilCliente = criaPerfilCliente();
        PerfilEntity perfilDono = criaPerfilDono();

        perfilJpaRepository.save(perfilCliente);
        perfilJpaRepository.save(perfilDono);

        // Act
        List<PerfilEntity> listaPerfis = perfilRepository.buscarTodos();

        // Assert
        assertThat(listaPerfis).isNotNull();
        assertThat(listaPerfis).hasSize(2);
        assertThat(listaPerfis)
                .extracting(PerfilEntity::getNome)
                .contains("Dono", "Cliente");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPerfis() {
        // Act
        List<PerfilEntity> resultado = perfilRepository.buscarTodos();

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado).isEmpty();
    }

    @Test
    void deveSalvarNovoPerfil() {
        // Arrange
        PerfilEntity novoPerfil = new PerfilEntity(null, "Novo perfil");

        // Act
        PerfilEntity perfilSalvo = perfilJpaRepository.save(novoPerfil);

        // Assert
        assertThat(perfilSalvo).isNotNull();
        assertThat(perfilSalvo.getId()).isNotNull();
        assertThat(perfilSalvo.getNome()).isEqualTo("Novo perfil");

        Optional<PerfilEntity> perfilEncontrado = perfilJpaRepository.findById(perfilSalvo.getId());
        assertThat(perfilEncontrado).isPresent();
        assertThat(perfilEncontrado.get().getNome()).isEqualTo("Novo perfil");
    }

    @Test
    void deveAtualizarPerfilComSucesso() {
        // Arrange
        PerfilEntity perfilDono = criaPerfilDono();
        PerfilEntity perfilSalvo = perfilJpaRepository.save(perfilDono);

        perfilSalvo.setNome("Nome atualizado");

        // Act
        PerfilEntity perfilAtualizado = perfilJpaRepository.save(perfilSalvo);

        // Assert
        assertThat(perfilAtualizado).isNotNull();
        assertThat(perfilAtualizado.getId()).isEqualTo(perfilSalvo.getId());
        assertThat(perfilAtualizado.getNome()).isEqualTo("Nome atualizado");
    }

    @Test
    void deveChecarIdPerfilJaCadastrado() {
        // Arrange
        PerfilEntity perfilSalvo = perfilJpaRepository.save(criaPerfilCliente());
        Integer idExistente = perfilSalvo.getId();

        // Act
        boolean resultadoExistente = perfilRepository.idJaCadastrado(idExistente);

        // Assert
        assertThat(resultadoExistente).isTrue();
    }

    @Test
    void deveChecarIdPerfilNaoCadastrado() {
        // Arrange
        Integer idInexistente = 9999;

        // Act
        boolean resultadoInexistente = perfilRepository.idJaCadastrado(idInexistente);

        // Assert
        assertThat(resultadoInexistente).isFalse();
    }

    private PerfilEntity criaPerfilCliente() {
        return new PerfilEntity(
                null,
                "Cliente"
        );
    }

    private PerfilEntity criaPerfilDono() {
        return new PerfilEntity(
                null,
                "Dono"
        );
    }
}
