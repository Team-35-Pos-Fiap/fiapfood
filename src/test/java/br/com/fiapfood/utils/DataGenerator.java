package br.com.fiapfood.utils;

import br.com.fiapfood.entities.db.EnderecoEntity;
import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.LoginRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
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

    public static LoginEntity validLoginEntity() {

        return new LoginEntity(
                UUID.fromString("4b9094a8-7b45-491e-8e5b-042ac5c65ae9"),
                "us0001",
                "123"
        );
    }

    public static PerfilEntity validPerfilEntity() {
        return new PerfilEntity(
                1,
                "Dono"
        );
    }

    public static UsuarioEntity validUsuarioEntity() {
        return new UsuarioEntity(
                UUID.fromString("cf05db14-7993-4564-bff9-c258b5c7387c"),
                "John Doe",
                "johndoe@email.com",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                validEnderecoEntity(),
                validPerfilEntity(),
                validLoginEntity()
        );
    }

    public static UsuarioRecordRequest validUsuarioRecordRequest() {
        return new UsuarioRecordRequest(
               "John Doe",
               "johndoe@email.com",
               1,
               validEnderecoRecordRequest(),
               validLoginRecordRequest()
        );
    }

    public static EnderecoEntity validInexistenteEnderecoEntity() {
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

    public static LoginEntity validInexistenteLoginEntity() {
        return new LoginEntity(
                null,
                "us0010",
                "123"
        );
    }

    public static UsuarioEntity validInexistenteUsuarioEntity() {
        return new UsuarioEntity(
                null,
                "Nome Teste",
                "nome@email.com",
                LocalDateTime.now(),
                null,
                true,
                validInexistenteEnderecoEntity(),
                validPerfilEntity(),
                validInexistenteLoginEntity()
        );
    }
}
