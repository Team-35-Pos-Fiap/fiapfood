package br.com.fiapfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.UsuarioDomain;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.impl.PerfilRepository;
import br.com.fiapfood.repositories.impl.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PerfilRepository perfilRepository;
	
	public UsuarioRecordResponse buscarPorId(Integer id) {
		UsuarioEntity usuarioEntity = usuarioRepository.recuperaDadosUsuarioPorId(id);
		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuarioEntity);
		
		return UsuarioMapper.toUsuarioRecord(usuarioDomain);
	}
	
	public UsuarioRecordPaginacaoResponse buscarUsuarios(final Integer pagina) {
		return UsuarioMapper.toUsuario(usuarioRepository.recuperaDadosUsuarios(pagina));
	}
	
	public void cadastrar(UsuarioRecordRequest usuario) {
		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuario);
		UsuarioEntity usuarioEntity = UsuarioMapper.toUsuario(usuarioDomain);
		
		salvar(usuarioEntity);
	}
	
	public void atualizarStatus(Integer id, boolean isAtivo) {
		UsuarioEntity usuario; 
		
		if(isAtivo) { 
			usuario = usuarioRepository.recuperaDadosUsuarioInativoPorId(id);
			
			usuario.reativar();
		} else {
			usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
			
			usuario.inativar();
		}
		
		salvar(usuario);
	}
	
	public void salvar(UsuarioEntity usuario) {
		usuarioRepository.salvar(usuario);
	}
			
	public void atualizarPerfil(Integer id, Integer idPerfil) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		if(!idPerfil.equals(usuario.getPerfil().getId())) {
			PerfilEntity perfil = perfilRepository.buscarPorId(idPerfil);
			
			usuario.atualizarPerfil(perfil);
			
			salvar(usuario);
		}
	}

	public void atualizarNome(@Valid @NotNull Integer id, String nome) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		usuario.atualizarNome(nome);
			
		salvar(usuario);
	}

	public void atualizarEmail(@Valid @NotNull Integer id, String email) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		usuario.atualizarEmail(email);
		
		salvar(usuario);
	}
}