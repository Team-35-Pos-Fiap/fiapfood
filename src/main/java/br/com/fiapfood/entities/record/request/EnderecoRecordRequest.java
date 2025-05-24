package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRecordRequest(@NotBlank(message = "O campo cidade precisa ser informado.")
									String cidade,

									@NotBlank(message = "O campo cep precisa ser informado.") 
									String cep, 

								    @NotBlank(message = "O campo bairro precisa ser informado.")
								    String bairro, 
								  
								    @NotBlank(message = "O campo endereco precisa ser informado.")
								    String endereco, 
								  
									@NotBlank(message = "O campo estado precisa ser informado.")
									String estado,
								  
								    Integer numero,
								    
								    String complemento,
								    
									@NotNull(message = "O campo semNumero precisa ser informado.")
									Boolean semNumero) { }