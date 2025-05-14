package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRecordRequest(@Schema(description = "Nome do usuário", example = "Thiago") String nome, 
								   @Schema(description = "Email do usuário", example = "thiago@restaurantetudibaum.com") String email, 
								   //@Schema(description = "Login do usuário", example = "us000001") String login, 
								   //@Schema(description = "Senha do usuário", example = "1234") String senha, 
								   @Schema(description = "Dados do endereço do usuário") DadosEnderecoRecord dadosEndereco,
								   @Schema(description = "IDs dos perfil de acesso do usuário.", example = "1") Integer perfil,
								   @Schema(description = "Dados do login do usuário.", example = "1") LoginRecordRequest dadosLogin) {
	public UsuarioRecordRequest(@NotBlank(message = "O campo nome precisa estar preenchido.")
								@Size(min = 3, max = 150, message = "O campo nome precisa ter entre 3 e 150 caracteres.") 
								String nome, 
						 		
								@NotBlank(message = "O campo email precisa estar preenchido.")
						 		@Size(min = 3, max = 70, message = "O campo email precisa ter entre 3 e 70 caracteres.") 
								String email, 
						 		
			/*
			 * @NotBlank(message = "O campo login precisa estar preenchido.")
			 * 
			 * @Size(min = 8, max = 8, message = "O campo login precisa ter 8 caracteres.")
			 * String login,
			 * 
			 * @NotBlank(message = "O campo senha precisa estar preenchido.")
			 * 
			 * @Size(min = 6, max = 10, message =
			 * "O campo senha precisa ter entre 6 e 10 caracteres.") String senha,
			 */
						 		
								@NotNull(message = "Os dados do endereço precisam estar preenchidos.")
								DadosEnderecoRecord dadosEndereco,
								
								@NotNull(message = "É necessário informar o perfil de acesso para o usuário.")
								Integer perfil,
								
								LoginRecordRequest dadosLogin) {
		this.nome = nome;
		this.email = email;
		//this.login = login;
		//this.senha = senha;
		this.dadosEndereco = dadosEndereco;
		this.perfil = perfil;
		this.dadosLogin = dadosLogin;
	}
}