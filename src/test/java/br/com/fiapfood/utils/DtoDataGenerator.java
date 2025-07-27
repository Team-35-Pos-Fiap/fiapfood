package br.com.fiapfood.utils;

import br.com.fiapfood.core.entities.dto.endereco.DadosEnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.login.LoginCoreDto;
import br.com.fiapfood.core.entities.dto.perfil.PerfilCoreDto;
import br.com.fiapfood.core.entities.dto.tipo_culinaria.TipoCulinariaCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.CadastrarUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
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
                UUID.fromString("88ecf851-3a78-475b-be3c-9a4a9aedec44"),
                "us0010",
                "123"
        );
    }

    public static LoginCoreDto loginCoreDtoValido() {
        return new LoginCoreDto(
                UUID.fromString("88ecf851-3a78-475b-be3c-9a4a9aedec44"),
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

    public static DadosEnderecoCoreDto dadosEnderecoCoreDtoValido() {
        return new DadosEnderecoCoreDto(
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
                UUID.fromString("acc1fc92-d526-46ea-92e9-ac23fd838885"),
                "São Gonçalo",
                "24455450",
                "Nova Cidade",
                "Rua Aquidabã",
                "Rio de Janeiro",
                79,
                "Casa 8"
        );
    }

    public static EnderecoCoreDto enderecoCoreDtoValido() {
        return new EnderecoCoreDto(
                UUID.fromString("acc1fc92-d526-46ea-92e9-ac23fd838885"),
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

    public static PerfilCoreDto perfilCoreDtoValido() {
        return new PerfilCoreDto(
                1,
                "Admin",
                LocalDate.now(),
                null
        );
    }

    public static CadastrarUsuarioDto cadastrarUsuarioDtoValido() {
        return new CadastrarUsuarioDto(
                "John Doe",
                1,
                loginDtoValido(),
                "john.doe@email.com",
                dadosEnderecoDtoValido()
        );
    }

    public static CadastrarUsuarioCoreDto cadastrarUsuarioCoreDtoValido() {
        return new CadastrarUsuarioCoreDto(
                "John Doe",
                1,
                loginCoreDtoValido(),
                "john.doe@email.com",
                dadosEnderecoCoreDtoValido()
        );
    }

    public static UsuarioDto usuarioDtoValido() {
        return new UsuarioDto(
                UUID.fromString("c626f4f2-9693-4fbd-9086-ee8b0bf5febb"),
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

    public static DadosUsuarioCoreDto dadosUsuarioCoreDtoValido() {
        return new DadosUsuarioCoreDto(
                UUID.fromString("c626f4f2-9693-4fbd-9086-ee8b0bf5febb"),
                "John Doe",
                perfilCoreDtoValido(),
                loginCoreDtoValido(),
                true,
                "john.doe@email.com",
                LocalDateTime.now(),
                null,
                enderecoCoreDtoValido()
        );
    }

    public static TipoCulinariaDto tipoCulinariaDtoValido() {
        return new TipoCulinariaDto(1, "Brasileira");
    }

    public static TipoCulinariaCoreDto tipoCulinariaCoreDtoValido() {
        return new TipoCulinariaCoreDto(1, "Brasileira");
    }


}