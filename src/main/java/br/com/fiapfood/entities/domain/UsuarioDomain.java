package br.com.fiapfood.entities.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsuarioDomain {
	private Integer id;
	private String nome;
	private String email;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataAtualizacao;
	private Boolean isAtivo;
	private DadosEnderecoDomain dadosEndereco;
	private PerfilDomain perfil;
	private LoginDomain dadosLogin;
}