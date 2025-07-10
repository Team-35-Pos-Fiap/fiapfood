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
    private UUID idEndereco;
    private String tipoCozinha;
    private LocalDateTime horarioFuncionamento;
    private UUID idDonoRestaurante;

    public void atualizarDados (String nome, UUID idEndereco, String tipoCozinha, LocalDateTime horarioFuncionamento, UUID idDonoRestaurante) {
        this.nome = nome;
        this.idEndereco = idEndereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.idDonoRestaurante = idDonoRestaurante;
    }
}
