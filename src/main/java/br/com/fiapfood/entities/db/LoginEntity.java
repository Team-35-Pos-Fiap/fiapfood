package br.com.fiapfood.entities.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login")
public class LoginEntity {
	@Id
	@Column(name = "id_usuario")
	private Integer id;
	private String matricula;
	private String senha;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id_usuario")
	private UsuarioEntity usuario;

	public void atualizarDados(String matricula, String senha) {
		this.matricula = matricula;
		this.senha = senha;
	}
}