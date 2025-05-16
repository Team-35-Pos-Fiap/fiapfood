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
@Table(name = "endereco")
public class EnderecoEntity {
	@Id
	@Column(name = "id_usuario")
	private Integer id;
	private String cidade;
	private String cep;
	private String bairro;
	private String endereco;
	private String estado;
	private Integer numero;
	private String complemento;
	@Column(name = "sem_numero", columnDefinition = "tinyint")
	private Boolean semNumero;

	@OneToOne
	@MapsId
	@JoinColumn(name = "id_usuario")
	private UsuarioEntity usuario;
	
	public void atualizarDados(String endereco, String cidade, String bairro, 
							   String estado, Integer numero, String cep, String complemento, 
							   Boolean semNumero) {
		this.endereco = endereco;
		this.cidade = cidade;
		this.bairro = bairro;
		this.estado = estado;
		this.numero = numero;
		this.cep = cep;
		this.complemento = complemento;
		this.semNumero = semNumero;
	}
	
	public void atualizarUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}
}