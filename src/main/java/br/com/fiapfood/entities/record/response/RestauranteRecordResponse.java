package br.com.fiapfood.entities.record.response;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestauranteRecordResponse(
        UUID id,
        String nome,
        EnderecoRecordRequest endereco,
        String tipoCozinha,
        LocalDateTime horarioFuncionamento,
        UsuarioRecordResponse donoRestaurante
) { }