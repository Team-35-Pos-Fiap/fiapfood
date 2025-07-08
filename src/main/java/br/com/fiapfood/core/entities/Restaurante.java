package br.com.fiapfood.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Restaurante {

    private UUID id;
    private String nome;
    private Endereco endereco;
    private String tipoCozinha;
    private LocalDateTime horarioFuncionamento;
    private Usuario donoRestaurante;
}
