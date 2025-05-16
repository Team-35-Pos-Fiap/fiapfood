package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record EnderecoRecordRequest(@Schema(description = "Cidade do usuário", example = "Cidade do usuário") 
									@NotBlank(message = "O campo cidade precisa ser informado.")  
									String cidade, 

							        @Schema(description = "Cep do usuário", example = "24455000") 
									@NotBlank(message = "O campo cep precisa ser informado.") 
									String cep, 

								    @Schema(description = "Bairro do usuário", example = "Bairro do usuário") 
								    @NotBlank(message = "O campo bairro precisa ser informado.") 
								    String bairro, 
								  
								    @Schema(description = "Endereço do usuário", example = "Endereço do usuário") 
								    @NotBlank(message = "O campo endereco precisa ser informado.") 
								    String endereco, 
								  
								    @NotBlank(message = "O campo estado precisa ser informado.") 
								  @Schema(description = "Estado do usuário", example = "RJ") 									String estado, 
								  
								    @Schema(description = "Número da residência do usuário", example = "79") 
								    Integer numero, 
								    
								    @Schema(description = "Complemento do endereço do usuário", example = "Casa 8") 
								    String complemento, 
								    
								    @Schema(description = "Indicador se a residência possui número", example = "true") 
									Boolean semNumero) { }