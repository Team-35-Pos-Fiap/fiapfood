package br.com.fiapfood.core.entities;

import java.time.LocalTime;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Atendimento {
	private UUID id;
	private String dia;
	private LocalTime inicioAtendimento;
	private LocalTime terminoAtendimento;
	
	public Atendimento(UUID id, String dia, LocalTime inicioAtendimento, LocalTime terminoAtendimento) {
		this.id = id;
		this.dia = dia;
		this.inicioAtendimento = inicioAtendimento;
		this.terminoAtendimento = terminoAtendimento;
	}
	
	public static Atendimento criar(UUID id, String dia, LocalTime inicioAtendimento, LocalTime terminoAtendimento) {
		return new Atendimento(id, dia, inicioAtendimento, terminoAtendimento);
	}
	
	public void atualizarDados(String dia, LocalTime inicioAtendimento, LocalTime terminoAtendimento) {
		this.dia = dia;
		this.inicioAtendimento = inicioAtendimento;
		this.terminoAtendimento = terminoAtendimento;
	}
}