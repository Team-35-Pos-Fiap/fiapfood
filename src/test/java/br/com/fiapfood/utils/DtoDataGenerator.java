package br.com.fiapfood.utils;

import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.endereco.EnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.login.LoginDto;
import br.com.fiapfood.infraestructure.controllers.request.perfil.PerfilDto;
import br.com.fiapfood.infraestructure.controllers.request.tipo_culinaria.TipoCulinariaDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.CadastrarUsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DtoDataGenerator {
    public static LoginDto loginDtoValido() {
        return new LoginDto(
                UUID.randomUUID(),
                "us0010",
                "123"
        );
    }

    public static DadosEnderecoDto dadosEnderecoDtoValido() {
        return new DadosEnderecoDto(
                "São Gonçalo",
                "24455450",
                "Nova Cidade",
                "Rua Aquidabã",
                "Rio de Janeiro",
                79,
                "Casa 8"
        );
    }

    public static EnderecoDto enderecoDtoValido() {
        return new EnderecoDto(
                UUID.randomUUID(),
                "São Gonçalo",
                "24455450",
                "Nova Cidade",
                "Rua Aquidabã",
                "Rio de Janeiro",
                79,
                "Casa 8"
        );
    }

    public static PerfilDto perfilDtoValido() {
        return new PerfilDto(
                1,
                "Admin",
                LocalDate.now(),
                null
        );
    }

    public static CadastrarUsuarioDto cadastrarUsuarioDtoValido() {
        UUID id = UUID.randomUUID();
        return new CadastrarUsuarioDto(
                "John Doe",
                1,
                loginDtoValido(),
                "john.doe@email.com",
                dadosEnderecoDtoValido()
        );
    }

    public static UsuarioDto usuarioDtoValido() {
        return new UsuarioDto(
                UUID.randomUUID(),
                "John Doe",
                perfilDtoValido(),
                loginDtoValido(),
                true,
                "john.doe@email.com",
                LocalDateTime.now(),
                null,
                enderecoDtoValido()
        );
    }

    public static TipoCulinariaDto tipoCulinariaDtoValido() {
        return new TipoCulinariaDto(1, "Brasileira");
    }


}