package br.com.fiapfood.core.entities.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestauranteDto(
        UUID id,
        String nome,
        EnderecoDto endereco,
        String tipoCozinha,
        LocalDateTime horarioFuncionamento,
        UsuarioDto donoRestaurante
) { }