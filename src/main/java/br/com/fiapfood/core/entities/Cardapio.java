package br.com.fiapfood.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Cardapio {

    private UUID id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponivelApenasRestaurante;
    private String fotoPrato;

    public void atualizarDados(String nome, String descricao, Double preco, Boolean disponivelApenasRestaurante, String fotoPrato) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.disponivelApenasRestaurante = disponivelApenasRestaurante;
        this.fotoPrato = fotoPrato;
    }
}
