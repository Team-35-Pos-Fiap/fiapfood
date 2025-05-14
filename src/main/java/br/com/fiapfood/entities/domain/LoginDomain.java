package br.com.fiapfood.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginDomain {
	private Integer id;
	private String matricula;
	private String senha;
	private UsuarioDomain usuario;
}