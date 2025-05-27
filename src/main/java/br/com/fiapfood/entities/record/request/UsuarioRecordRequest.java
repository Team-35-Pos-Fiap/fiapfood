package br.com.fiapfood.entities.record.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRecordRequest(@NotBlank(message = "O campo nome precisa estar preenchido.")
								   @Size(min = 3, max = 150, message = "O campo nome precisa ter entre 3 e 150 caracteres.") 
								   String nome, 

								   @NotBlank(message = "O campo email precisa estar preenchido.")
								   @Email(message = "O e-mail precisa ser válido")
							       @Size(min = 3, max = 70, message = "O campo email precisa ter entre 3 e 70 caracteres.") 
								   String email, 
									   
								   @NotNull(message = "É necessário informar o perfil de acesso para o usuário.")
								   Integer perfil,

								   @NotNull(message = "É necessário informar os dados de endereço para o usuário.")
								   @Valid
								   EnderecoRecordRequest dadosEndereco,

								   @NotNull(message = "É necessário informar os dados de login para o usuário.")
								   @Valid
								   LoginRecordRequest dadosLogin) { }