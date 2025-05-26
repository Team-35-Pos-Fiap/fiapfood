package br.com.fiapfood.services;

import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.repositories.interfaces.IUsuarioRepository;
import br.com.fiapfood.services.interfaces.IEnderecoService;
import br.com.fiapfood.services.interfaces.ILoginService;
import br.com.fiapfood.services.interfaces.IPerfilService;
import br.com.fiapfood.services.interfaces.IUsuarioService;
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

import java.util.UUID;

@Service
public class UsuarioService implements IUsuarioService {

	private final IUsuarioRepository usuarioRepository;
	private final IPerfilService perfilService;
	private final IEnderecoService enderecoService;

	public UsuarioService(IUsuarioRepository usuarioRepository, IPerfilService perfilService, IEnderecoService enderecoService) {
		this.usuarioRepository = usuarioRepository;
		this.perfilService = perfilService;
		this.enderecoService = enderecoService;
	}

	@Override
	public UsuarioRecordResponse buscarPorId(UUID id) {
		UsuarioEntity usuarioEntity = usuarioRepository.recuperaDadosUsuarioPorId(id);
		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuarioEntity);
		
		return UsuarioMapper.toUsuarioRecord(usuarioDomain);
	}

	@Override
	public UsuarioRecordPaginacaoResponse buscarUsuarios(final Integer pagina) {
		return UsuarioMapper.toUsuario(usuarioRepository.recuperaDadosUsuarios(pagina));
	}

	@Override
	public void cadastrar(UsuarioRecordRequest usuario) {
		// validar se email ja nao esta cadastrado
		if(usuarioRepository.emailJaCadastrado(usuario.email())){
			throw new IllegalArgumentException("Email ja cadastrado");
		}

		// validar se matricula ja nao esta cadastrada
//		if(loginService.matriculaJaCadastrada(usuario.dadosLogin().matricula())){
//			throw new IllegalArgumentException("Matricula ja cadastrada");
//		}

		UsuarioDomain usuarioDomain = UsuarioMapper.toUsuario(usuario);
		UsuarioEntity usuarioEntity = UsuarioMapper.toUsuario(usuarioDomain);
		
		salvar(usuarioEntity);
	}

	@Override
	public void atualizarStatus(UUID id, boolean isAtivo) {
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

	@Override
	public void salvar(UsuarioEntity usuario) {
		usuarioRepository.salvar(usuario);
	}

	@Override
	public void atualizarPerfil(UUID id, Integer idPerfil) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		if(!idPerfil.equals(usuario.getPerfil().getId())) {
			PerfilEntity perfil = perfilService.buscarPorId(idPerfil);
			
			usuario.atualizarPerfil(perfil);
			
			salvar(usuario);
		}
	}

	@Override
	public void atualizarNome(UUID id, String nome) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);
		
		usuario.atualizarNome(nome);
			
		salvar(usuario);
	}

	@Override
	public void atualizarEmail(UUID id, String email) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioAtivoPorId(id);

		if(usuarioRepository.emailJaCadastrado(email)){
			throw new IllegalArgumentException("Email ja cadastrado");
		}
		
		usuario.atualizarEmail(email);
		
		salvar(usuario);
	}

	@Override
	public UsuarioEntity buscarUsuarioPorIdLogin(UUID loginId){
		return usuarioRepository.recuperarDadosUsuarioPorIdLogin(loginId);
	}

	@Override
	public void atualizarDadosEndereco(UUID id, EnderecoRecordRequest dadosEndereco) {
		UsuarioEntity usuario = usuarioRepository.recuperaDadosUsuarioPorId(id);

		enderecoService.atualizarEndereco(usuario.getDadosEndereco(), dadosEndereco);
	}
}