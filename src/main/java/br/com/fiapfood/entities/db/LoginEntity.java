package br.com.fiapfood.entities.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class LoginEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true)
	private String matricula;
	private String senha;

	public void atualizarSenha(String senha) {
		this.senha = senha;
	}

	public void atualizarMatricula(String matricula) {
		this.matricula = matricula;
	}
}