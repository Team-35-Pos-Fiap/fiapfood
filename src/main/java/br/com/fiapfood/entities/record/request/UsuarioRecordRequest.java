package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRecordRequest(@Schema(description = "Nome do usuário", example = "Thiago") 
								   @NotBlank(message = "O campo nome precisa estar preenchido.")
								   @Size(min = 3, max = 150, message = "O campo nome precisa ter entre 3 e 150 caracteres.") 
								   String nome, 

								   @Schema(description = "Email do usuário", example = "thiago@fiapfood.com") 
								   @NotBlank(message = "O campo email precisa estar preenchido.")
								   @Email(message = "O e-mail precisa ser válido")
							       @Size(min = 3, max = 70, message = "O campo email precisa ter entre 3 e 70 caracteres.") 
								   String email, 
									   
								   @NotNull(message = "É necessário informar o perfil de acesso para o usuário.")
								   @Schema(description = "IDs dos perfil de acesso do usuário.", example = "1") 
								   Integer perfil,

								   @NotNull(message = "É necessário informar os dados de endereço para o usuário.")
								   @Valid
								   @Schema(
										   description = "Dados de endereço do usuário.",
										   example = "{'cidade': 'São Gonçalo', 'cep': '24455451', 'bairro': 'Nova Cidade'," +
												   " 'endereco': 'Rua Aquidaba', 'estado': 'Rio de Janeiro', 'numero': '7'," +
												   " 'complemento': 'Casa 8', 'semNumero': 'false'}"
								   )
								   EnderecoRecordRequest dadosEndereco,

								   @NotNull(message = "É necessário informar os dados de login para o usuário.")
								   @Valid
								   @Schema(
										   description = "Dados de login do usuário.",
										   example = "{'matricula': 'user', 'senha': '123'}"
								   )
								   LoginRecordRequest dadosLogin) { }