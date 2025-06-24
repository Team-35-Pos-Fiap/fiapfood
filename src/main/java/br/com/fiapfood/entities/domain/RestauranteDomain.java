package br.com.fiapfood.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestauranteDomain {

    private UUID id;
    private String nome;
    private EnderecoDomain endereco;
    private String tipoCozinha;
    private LocalDateTime horarioFuncionamento;
    private UsuarioDomain donoRestaurante;
}
