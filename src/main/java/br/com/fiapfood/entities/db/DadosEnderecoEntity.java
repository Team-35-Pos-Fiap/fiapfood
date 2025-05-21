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
@Table(name = "endereco")
public class DadosEnderecoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String cidade;
	private String cep;
	private String bairro;
	private String endereco;
	private String estado;
	private Integer numero;
	private String complemento;
	@Column(name = "sem_numero", columnDefinition = "tinyint")
	private Boolean semNumero;

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
}