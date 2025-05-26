package br.com.fiapfood.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EnderecoDomain {
	private UUID id;
	private String cidade;
	private String cep;
	private String bairro;
	private String endereco;
	private String estado;
	private Integer numero;
	private String complemento;
	private Boolean semNumero;
}