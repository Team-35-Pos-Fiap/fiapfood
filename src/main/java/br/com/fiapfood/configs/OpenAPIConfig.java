package br.com.fiapfood.configs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

	@Bean
	OpenAPI configurarOpenAPI() {
		Server dadosServidor = new Server();
		
		dadosServidor.setUrl("http://localhost:8080/fiapfood");
		dadosServidor.setDescription("Servidor local");
		
		Contact contato = new Contact();
		
		contato.setName("Thiago Motta");
		contato.setEmail("thiago.motta.souza.23@gmail.com");
		
		Info informacao = new Info().contact(contato)
									.description("Aplicação do Tech Challenge - Fiapfood.")
									.title("Restaurante Fiapfood")
									.version("1.0");
		
		return new OpenAPI().info(informacao)
							.servers(Arrays.asList(dadosServidor));
	}
}