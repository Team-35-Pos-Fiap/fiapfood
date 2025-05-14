package br.com.fiapfood.entities.record.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DadosEnderecoRecord(@Schema(description = "Cidade do usuário", example = "São Gonçalo") String cidade, 
							      @Schema(description = "Cep do usuário", example = "24455450") String cep, 
								  @Schema(description = "Bairro do usuário", example = "Nova Cidade") String bairro, 
								  @Schema(description = "Endereço do usuário", example = "Rua Aquidabã") String endereco, 
								  @Schema(description = "Estado do usuário", example = "RJ") String estado, 
								  @Schema(description = "Número da residência do usuário", example = "79") Integer numero, 
								  @Schema(description = "Complemento do endereço do usuário", example = "Casa 8") String complemento, 
								  @Schema(description = "Indicador se a residência possui número", example = "true") Boolean semNumero) {
	public DadosEnderecoRecord(@NotBlank(message = "O campo cidade precisa ser informado.") String cidade, 
							   @NotBlank(message = "O campo cep precisa ser informado.") String cep, 
							   @NotBlank(message = "O campo bairro precisa ser informado.") String bairro, 
					    	   @NotBlank(message = "O campo endereco precisa ser informado.") String endereco, 
							   @NotBlank(message = "O campo estado precisa ser informado.") String estado, 
							   Integer numero, 
							   String complemento, 
							   Boolean semNumero) {
		//validar(cidade, cep, bairro, endereco, estado, numero, complemento, semNumero);
		
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.complemento = complemento;
		this.endereco = endereco;
		this.estado = estado;
		this.numero = numero;
		this.semNumero = semNumero;
	}
}