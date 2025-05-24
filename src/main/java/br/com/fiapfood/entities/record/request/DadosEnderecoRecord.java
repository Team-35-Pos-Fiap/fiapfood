package br.com.fiapfood.entities.record.request;

import jakarta.validation.constraints.NotBlank;

public record DadosEnderecoRecord(String cidade,
							      String cep,
								  String bairro,
								  String endereco,
								  String estado,
								  Integer numero,
								  String complemento,
								  Boolean semNumero) {
	public DadosEnderecoRecord(@NotBlank(message = "O campo cidade precisa ser informado.") String cidade, 
							   @NotBlank(message = "O campo cep precisa ser informado.") String cep, 
							   @NotBlank(message = "O campo bairro precisa ser informado.") String bairro, 
					    	   @NotBlank(message = "O campo endereco precisa ser informado.") String endereco, 
							   @NotBlank(message = "O campo estado precisa ser informado.") String estado, 
							   Integer numero, 
							   String complemento, 
							   Boolean semNumero) {

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