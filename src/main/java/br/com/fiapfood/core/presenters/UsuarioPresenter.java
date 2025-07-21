package br.com.fiapfood.core.presenters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.fiapfood.core.entities.Endereco;
import br.com.fiapfood.core.entities.Login;
import br.com.fiapfood.core.entities.Perfil;
import br.com.fiapfood.core.entities.Usuario;
import br.com.fiapfood.core.entities.dto.endereco.EnderecoCoreDto;
import br.com.fiapfood.core.entities.dto.login.LoginCoreDto;
import br.com.fiapfood.core.entities.dto.paginacao.PaginacaoCoreDto;
import br.com.fiapfood.core.entities.dto.perfil.PerfilCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.CadastrarUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioInputDto;
import br.com.fiapfood.core.entities.dto.usuario.DadosUsuarioResumidoCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoCoreDto;
import br.com.fiapfood.core.entities.dto.usuario.UsuarioPaginacaoInputDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.CadastrarUsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.DadosUsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioDto;
import br.com.fiapfood.infraestructure.controllers.request.usuario.UsuarioPaginacaoDto;
import br.com.fiapfood.infraestructure.entities.EnderecoEntity;
import br.com.fiapfood.infraestructure.entities.LoginEntity;
import br.com.fiapfood.infraestructure.entities.PerfilEntity;
import br.com.fiapfood.infraestructure.entities.UsuarioEntity;

public class UsuarioPresenter {

	public static DadosUsuarioCoreDto toUsuarioDto(UsuarioEntity usuario, PerfilCoreDto perfil, LoginCoreDto login, EnderecoCoreDto endereco) {
		return new DadosUsuarioCoreDto(usuario.getId(), usuario.getNome(), perfil, login, usuario.getIsAtivo(), usuario.getEmail(), usuario.getDataCriacao(), usuario.getDataAtualizacao(), endereco);
	}

	public static Usuario toUsuario(DadosUsuarioInputDto usuario) {
		return Usuario.criar(usuario.id(), usuario.nome(), usuario.idPerfil(), usuario.idLogin(), usuario.isAtivo(), usuario.email(), usuario.dataCriacao(), usuario.dataAtualizacao(), usuario.idEndereco());
	}
	
	public static DadosUsuarioCoreDto toUsuarioDto(Usuario usuario, Perfil perfil, Login login, Endereco endereco) {
		return new DadosUsuarioCoreDto(usuario.getId(), usuario.getNome(), PerfilPresenter.toPerfilDto(perfil), LoginPresenter.toLogin(login), 
							  usuario.getIsAtivo(), usuario.getEmail(), usuario.getDataCriacao(), usuario.getDataAtualizacao(), EnderecoPresenter.toEnderecoEntity(endereco));
	}
	
	public static UsuarioPaginacaoCoreDto toUsuarioPaginacaoDto(List<DadosUsuarioCoreDto> usuarios, PaginacaoCoreDto paginacao) {
		return new UsuarioPaginacaoCoreDto(usuarios, paginacao);
	}

	public static List<DadosUsuarioInputDto> toListUsuarioDto(List<UsuarioEntity> usuarios) {
		return usuarios.stream().map(u -> UsuarioPresenter.toUsuarioInputDto(u)).toList();
	}
	
	public static List<Usuario> toListUsuario(List<DadosUsuarioInputDto> usuarios) {
		return usuarios.stream().map(u -> UsuarioPresenter.toUsuario(u)).toList();
	}

	public static UsuarioEntity toUsuarioEntity(DadosUsuarioCoreDto usuario, EnderecoEntity endereco, PerfilEntity perfil, LoginEntity login) {
		return new UsuarioEntity(usuario.id(), usuario.nome(), usuario.email(), usuario.dataCriacao(), usuario.dataAtualizacao(), usuario.isAtivo(), endereco, perfil, login);
	}
	
	public static Usuario toUsuario(CadastrarUsuarioCoreDto usuario) {
		return Usuario.criar(null, usuario.nome(), usuario.perfil(), null, true, usuario.email(), LocalDateTime.now(), null, null);
	}

	public static DadosUsuarioResumidoCoreDto toUsuarioOutputDto(Usuario usuario, Login login) {
		return new DadosUsuarioResumidoCoreDto(usuario.getId(), usuario.getNome(), login.getMatricula(), usuario.getEmail());
	}

	public static DadosUsuarioResumidoCoreDto toUsuarioOutputDto(Usuario usuario) {
		return new DadosUsuarioResumidoCoreDto(usuario.getId(), null, null, null);
	}
	
	public static DadosUsuarioInputDto toUsuarioInputDto(UsuarioEntity usuario) {
		return new DadosUsuarioInputDto(usuario.getId(), usuario.getNome(), usuario.getPerfil().getId(), usuario.getDadosLogin().getId(), 
										usuario.getIsAtivo(), usuario.getEmail(), usuario.getDataCriacao(), usuario.getDataAtualizacao(), usuario.getDadosEndereco().getId());
	}

	public static UsuarioPaginacaoInputDto toUsuarioPaginacaoInputDto(Page<UsuarioEntity> dados) {
		List<DadosUsuarioInputDto> usuarios = dados.toList()
												   .stream()
												   .map(u -> UsuarioPresenter.toUsuarioInputDto(u))
												   .collect(Collectors.toList());

		PaginacaoCoreDto paginacao = new PaginacaoCoreDto(dados.getNumber() + 1, dados.getTotalPages(), Long.valueOf(dados.getTotalElements()).intValue());
		
		return new UsuarioPaginacaoInputDto(usuarios, paginacao);
	}

	public static UsuarioPaginacaoCoreDto toUsuarioPaginacaoOutputDto(List<DadosUsuarioCoreDto> usuarios, PaginacaoCoreDto paginacao) {
		return new UsuarioPaginacaoCoreDto(usuarios, paginacao);
	}

	public static UsuarioEntity toUsuarioResumidoEntity(UUID id) {
		return new UsuarioEntity(id, null, null, null, null, null, null, null, null);
	}

	public static DadosUsuarioDto toDadosUsuarioDto(DadosUsuarioResumidoCoreDto usuario) {
		return new DadosUsuarioDto(usuario.id(), usuario.nome(), usuario.matricula(), usuario.email());
	}

	public static UsuarioPaginacaoDto toUsuarioPaginacaoDto(UsuarioPaginacaoCoreDto dados) {
		return new UsuarioPaginacaoDto(UsuarioPresenter.toListUsuarioOutputDto(dados.usuarios()), 
									   PaginacaoPresenter.toPaginacaoDto(dados.paginacao()));
	}

	public static UsuarioDto toUsuarioDto(DadosUsuarioCoreDto usuario) {
		return new UsuarioDto(usuario.id(), usuario.nome(), PerfilPresenter.toPerfilDto(usuario.perfil()), LoginPresenter.toLoginDto(usuario.login()), 
							  usuario.isAtivo(), usuario.email(), usuario.dataCriacao(), usuario.dataAtualizacao(), EnderecoPresenter.toEnderecoDto(usuario.endereco()));
	}
	
	public static List<UsuarioDto> toListUsuarioOutputDto(List<DadosUsuarioCoreDto> usuarios) {
		return usuarios.stream().map(u -> UsuarioPresenter.toUsuarioDto(u)).toList();
	}

	public static CadastrarUsuarioCoreDto toCadastrarUsuarioDto(CadastrarUsuarioDto usuario) {
		return new CadastrarUsuarioCoreDto(usuario.nome(), usuario.perfil(), LoginPresenter.toLoginCoreDto(usuario.dadosLogin()), 
										   usuario.email(), EnderecoPresenter.toEnderecoCoreDto(usuario.dadosEndereco()));
	}
}