package br.com.fiapfood.core.entities.dto.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosUsuarioInputDto(UUID id, String nome, Integer idPerfil, UUID idLogin, 
								   Boolean isAtivo, String email, LocalDateTime dataCriacao, 
								   LocalDateTime dataAtualizacao, UUID idEndereco){
				
}