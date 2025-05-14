package br.com.fiapfood.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErroResponse {
	private String mensagem;
}