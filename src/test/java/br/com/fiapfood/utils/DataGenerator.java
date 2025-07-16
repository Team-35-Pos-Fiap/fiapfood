package br.com.fiapfood.utils;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.response.PerfilRecordResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;

import java.time.LocalDateTime;
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

    public static LoginEntity validLoginEntity() {
        return new LoginEntity(
                null,
                "us0010",
                "123"
        );
    }

    public static EnderecoEntity validEnderecoEntity() {
        return new EnderecoEntity(
                null,
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

    public static PerfilEntity validPerfilEntity() {
        return new PerfilEntity(
                2,
                "Cliente"
        );
    }

    public static UsuarioEntity validUsuarioEntity() {
        return new UsuarioEntity(
                null,
                "Nome Teste",
                "nome@email.com",
                LocalDateTime.now(),
                null,
                true,
                validEnderecoEntity(),
                validPerfilEntity(),
                validLoginEntity()
        );
    }
}
