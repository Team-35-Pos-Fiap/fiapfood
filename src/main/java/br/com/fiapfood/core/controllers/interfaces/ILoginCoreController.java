package br.com.fiapfood.core.controllers.interfaces;

public interface ILoginCoreController {
	void atualizarSenha(String matricula, String senha);
	void atualizarMatricula(String matricula, String matriculaNova);
	String validar(String matricula, String senha);
}