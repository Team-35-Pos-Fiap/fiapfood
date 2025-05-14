package br.com.fiapfood.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiapfood.entities.db.DadosEnderecoEntity;
import br.com.fiapfood.entities.db.LoginEntity;
import br.com.fiapfood.entities.db.PerfilEntity;
import br.com.fiapfood.entities.db.UsuarioEntity;
import br.com.fiapfood.entities.domain.UsuarioDomain;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.mappers.LoginMapper;
import br.com.fiapfood.mappers.UsuarioMapper;
import br.com.fiapfood.repositories.impl.DadosEnderecoRepository;
import br.com.fiapfood.repositories.impl.PerfilRepository;
import br.com.fiapfood.repositories.impl.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DadosEnderecoRepository dadosEnderecoRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	public UsuarioRecordResponse buscarPorId(Integer id) {
		UsuarioEntity usuarioEntity = usuarioRepository.recuperaDadosUsuarioPorId(id);
		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuarioEntity);
		
		return UsuarioMapper.toUsuarioRecord(usuarioDomain);
	}
	
	public UsuarioRecordPaginacaoResponse buscarUsuarios(final Integer pagina) {
		return UsuarioMapper.toUsuarioPaginacaoRecord(usuarioRepository.recuperaDadosUsuarios(pagina));
	}
	
	public void cadastrar(UsuarioRecordRequest usuario) {
		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuario);
		UsuarioEntity usuarioEntity = UsuarioMapper.toUsuario(usuarioDomain);
		
		salvar(usuarioEntity);
	}
	
	public void atualizar(Integer id, UsuarioRecordRequest usuarioRecord) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		setaDadosAtualizacao(usuarioRecord, usuario);
			
		salvar(usuario);
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
		
	private void setaDadosAtualizacao(UsuarioRecordRequest usuarioRecord, UsuarioEntity usuario) {
		usuario.atualizarDadosUsuario(usuarioRecord.nome(), usuarioRecord.email());
		trataDadosLogin(usuarioRecord, usuario);
		trataDadosEndereco(usuarioRecord, usuario);
		trataDadosPerfil(usuarioRecord, usuario);
	}

	private void trataDadosLogin(UsuarioRecordRequest usuarioRecord, UsuarioEntity usuario) {
		if(!usuarioRecord.dadosLogin().matricula().equals(usuario.getDadosLogin().getMatricula())) {
			LoginEntity dadosLogin = usuario.getDadosLogin();
			
			dadosLogin.atualizarDados(usuarioRecord.dadosLogin().matricula(), usuarioRecord.dadosLogin().senha());
			usuario.atualizarDadosLogin(dadosLogin);
		}
	}

	private void trataDadosEndereco(UsuarioRecordRequest usuarioRecord, UsuarioEntity usuario) {
		Optional<DadosEnderecoEntity> dados = dadosEnderecoRepository.recuperaDadosEndereco(usuario.getId());
		
		DadosEnderecoEntity dadosEndereco;
		
		if (dados.isPresent()) {
			dadosEndereco = dados.get();
		} else {
			dadosEndereco = new DadosEnderecoEntity();
		}

		dadosEndereco.atualizarDados(usuarioRecord.dadosEndereco().endereco(), 
									 usuarioRecord.dadosEndereco().cidade(), 
									 usuarioRecord.dadosEndereco().bairro(), 
									 usuarioRecord.dadosEndereco().estado(), 
									 usuarioRecord.dadosEndereco().numero(), 
									 usuarioRecord.dadosEndereco().cep(), 
									 usuarioRecord.dadosEndereco().complemento(), 
									 usuarioRecord.dadosEndereco().semNumero());
		usuario.atualizarDadosEndereco(dadosEndereco);		
	}
	
	private void trataDadosPerfil(UsuarioRecordRequest usuarioRecord, UsuarioEntity usuario) {
		if(!usuarioRecord.perfil().equals(usuario.getPerfil().getId())) {
			PerfilEntity perfil = perfilRepository.buscarPorId(usuarioRecord.perfil());
			
			usuario.atualizarPerfil(perfil);
		}
	}
}