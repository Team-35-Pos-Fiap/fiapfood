package br.com.fiapfood.entities.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cardapio")
public class CardapioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Boolean disponivelApenasRestaurante;

    @Column(nullable = false)
    private String fotoPrato;

    public void atualizarDados(String nome, String descricao, Double preco,
                               Boolean disponivelApenasRestaurante, String fotoPrato) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivelApenasRestaurante = disponivelApenasRestaurante;
        this.fotoPrato = fotoPrato;
    }


}
