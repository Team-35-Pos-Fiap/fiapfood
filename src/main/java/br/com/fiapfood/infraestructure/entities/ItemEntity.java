package br.com.fiapfood.infraestructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false, name = "disponivel_consumo_presencial", columnDefinition = "tinyint")
    private Boolean isDisponivelConsumoPresencial;

    @Column(nullable = false, name = "disponivel", columnDefinition = "tinyint")
    private Boolean isDisponivel;
    
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_imagem")
	private ImagemEntity imagem;
	
	@ManyToOne
	@JoinColumn(name = "id_restaurante")
	private RestauranteEntity restaurante;
}