package br.com.fiapfood.infraestructure.controllers.request.usuario;

import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.login.LoginDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastrarUsuarioDto(@NotBlank(message = "O campo nome precisa estar preenchido.")
								  String nome,
								  
 								  @NotNull(message = "O campo perfil precisa estar preenchido.")
								  @Positive(message = "O campo perfil precisa ter valor maior do que 0.") 
								  Integer perfil, 
								  
								  @NotNull(message = "O campo dadosLogin precisa estar preenchido.")
								  LoginDto dadosLogin, 

								  @NotBlank(message = "O campo email precisa estar preenchido.")
								  String email, 
								  
								  @NotNull(message = "O campo dadosEndereco precisa estar preenchido.")
								  DadosEnderecoDto dadosEndereco) {

}