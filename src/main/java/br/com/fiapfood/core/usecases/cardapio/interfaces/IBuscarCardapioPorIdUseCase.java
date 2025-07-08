package br.com.fiapfood.core.usecases.cardapio.interfaces;

import br.com.fiapfood.core.entities.dto.CardapioDto;

import java.util.UUID;

public interface IBuscarCardapioPorIdUseCase {

    CardapioDto buscarPorId(UUID id);
}