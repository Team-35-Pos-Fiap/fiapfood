package br.com.fiapfood.infraestructure.entities;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.EnderecoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurante")
public class RestauranteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private EnderecoEntity endereco;

    @Column(nullable = false)
    private String tipoCozinha;

    @Column(nullable = false)
    private LocalDateTime horarioFuncionamento;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dono_restaurante")
    private UsuarioEntity donoRestaurante;

    public void atualizarDados(String nome, EnderecoEntity endereco, String tipoCozinha,
                               LocalDateTime horarioFuncionamento, UsuarioEntity donoRestaurante) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoRestaurante = donoRestaurante;
    }


}
