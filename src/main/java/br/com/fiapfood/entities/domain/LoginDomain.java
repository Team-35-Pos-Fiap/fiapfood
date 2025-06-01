package br.com.fiapfood.entities.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginDomain {
	private UUID id;
	private String matricula;
	private String senha;
}