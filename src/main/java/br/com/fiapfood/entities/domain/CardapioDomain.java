package br.com.fiapfood.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardapioDomain {

    private UUID id;
    private String nome;
    private String descricao;
    private Double preco;
    private Boolean disponivelApenasRestaurante;
    private String fotoPrato;
}
