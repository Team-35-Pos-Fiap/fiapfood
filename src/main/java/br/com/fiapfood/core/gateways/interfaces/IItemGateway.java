package br.com.fiapfood.core.gateways.interfaces;import java.util.UUID;

import br.com.fiapfood.core.entities.Item;

public interface IItemGateway {
	Item buscarPorId(UUID id);
}