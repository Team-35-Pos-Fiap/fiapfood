package br.com.fiapfood.utils;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CoreEntityDataGenerator {
    public static Perfil corePerfilEntityValido() {
        return Perfil.criar(
                1,
                "Dono",
                LocalDate.now(),
                null
        );
    }

    public static Login coreLoginEntityValido() {
        return Login.criar(
                UUID.fromString("631fe67c-567b-4513-86e1-43354c72a5a3"),
                "us0001",
                "123"
        );
    }

    public static Endereco coreEnderecoEntityValido() {
        return new Endereco(
                UUID.fromString("263e71ee-0143-4c92-837c-fbb21f2b2f59"),
                "São Gonçalo",
                "24455450",
                "Nova Cidade",
                "Rua Aquidabã",
                "Rio de Janeiro",
                79,
                "Casa 8"
        );
    }

    public static Usuario coreUsuarioEntityAtivoValido() {
        return Usuario.criar(
                UUID.fromString("ad604453-c693-4bc4-9028-06d763299282"),
                "John Doe",
                1,
                coreLoginEntityValido(),
                true,
                "john.doe@email.com",
                LocalDateTime.now(),
                null,
                coreEnderecoEntityValido()
        );
    }

    public static Usuario coreUsuarioEntityInativoValido() {
        return Usuario.criar(
                UUID.fromString("ad604453-c693-4bc4-9028-06d763299282"),
                "John Doe",
                1,
                coreLoginEntityValido(),
                false,
                "john.doe@email.com",
                LocalDateTime.now(),
                null,
                coreEnderecoEntityValido()
        );
    }
}
