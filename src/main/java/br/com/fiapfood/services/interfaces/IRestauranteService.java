package br.com.fiapfood.services.interfaces;

import br.com.fiapfood.entities.db.RestauranteEntity;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;

import java.util.UUID;

public interface IRestauranteService {
    RestauranteRecordResponse buscarPorId(UUID id);
    RestauranteRecordPaginacaoResponse buscarTodos(Integer pagina);
    void cadastrar(RestauranteRecordRequest restaurante);
    void atualizarRestaurante(UUID id, RestauranteRecordRequest restaurante);
    void salvar(RestauranteEntity restaurante);
    void atualizarEnderecoRestaurante(UUID id, EnderecoRecordRequest dadosEndereco);
    void atualizarDonoRestaurante(UUID id, UsuarioRecordRequest donoRestaurante);
    void deletarRestaurante(UUID id);
}
