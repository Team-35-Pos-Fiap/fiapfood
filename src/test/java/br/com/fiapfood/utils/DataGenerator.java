package br.com.fiapfood.utils;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;

import java.util.UUID;

public class DataGenerator {
    public static LoginRecordRequest validLoginRecordRequest() {
        return new LoginRecordRequest(
                "us0010",
                "123"
        );
    }

    public static EnderecoRecordRequest validEnderecoRecordRequest() {
        return new EnderecoRecordRequest(
                "São Gonçalo",
                "24455450",
                "Nova Cidade",
                "Rua Aquidabã",
                "Rio de Janeiro",
                79,
                "Casa 8",
                false
        );
    }

    public static PerfilRecordResponse validPerfilRecordResponse() {
        return new PerfilRecordResponse(
                1,
                "Cliente"
        );
    }

    public static UsuarioRecordResponse validUsuarioRecordResponse() {
        UUID id = UUID.randomUUID();
        return new UsuarioRecordResponse(
                id,
                "Thiago Motta",
                "thiago@fiapfood.com",
                "us0001",
                true,
                validEnderecoRecordRequest(),
                validPerfilRecordResponse()
        );
    }

    public static EnderecoEntity validEnderecoEntity() {
        return new EnderecoEntity(
                UUID.fromString("f71d82cf-cede-42c8-97f8-182fda1b35bb"),
                "Cidade",
                "12345678",
                "Bairro",
                "Endereço",
                "Estado",
                1,
                "Complemento",
                false
        );
    }
}
