package br.com.fiapfood.entities.db;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = true)
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "ativo", columnDefinition = "tinyint")
	private Boolean isAtivo;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "id_usuario")
	private EnderecoEntity dadosEndereco;
	
	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private PerfilEntity perfil;
	
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "id_usuario")
	private LoginEntity dadosLogin;
	
	public void inativar() {
		this.isAtivo = false;
		this.dataAtualizacao = getDataAtual();
	}
	
	public void reativar() {
		this.isAtivo = true;
		this.dataAtualizacao = getDataAtual();
	}

	public void atualizarPerfil(PerfilEntity perfil) {
		this.perfil = perfil;
		this.dataAtualizacao = getDataAtual();
	}
	
	public void atualizarNome(String nome) {
		this.nome = nome;
		this.dataAtualizacao = getDataAtual();
	}
	
	public void atualizarEmail(String email) {
		this.email = email;
		this.dataAtualizacao = getDataAtual();
	}
	
	private LocalDateTime getDataAtual() {
		return LocalDateTime.now();
	}
}