package br.com.fiapfood.infraestructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.fiapfood.core.controllers.impl.ItemCoreController;
import br.com.fiapfood.core.controllers.impl.LoginCoreController;
import br.com.fiapfood.core.controllers.impl.PerfilCoreController;
import br.com.fiapfood.core.controllers.impl.RestauranteCoreController;
import br.com.fiapfood.core.controllers.impl.TipoCulinariaCoreController;
import br.com.fiapfood.core.controllers.impl.UsuarioCoreController;
import br.com.fiapfood.core.controllers.interfaces.IItemCoreController;
import br.com.fiapfood.core.controllers.interfaces.ILoginCoreController;
import br.com.fiapfood.core.controllers.interfaces.IPerfilCoreController;
import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.controllers.interfaces.ITipoCulinariaCoreController;
import br.com.fiapfood.core.controllers.interfaces.IUsuarioCoreController;
import br.com.fiapfood.core.gateways.impl.EnderecoGateway;
import br.com.fiapfood.core.gateways.impl.ImagemGateway;
import br.com.fiapfood.core.gateways.impl.ItemGateway;
import br.com.fiapfood.core.gateways.impl.LoginGateway;
import br.com.fiapfood.core.gateways.impl.PerfilGateway;
import br.com.fiapfood.core.gateways.impl.RestauranteGateway;
import br.com.fiapfood.core.gateways.impl.TipoCulinariaGateway;
import br.com.fiapfood.core.gateways.impl.UsuarioGateway;
import br.com.fiapfood.core.gateways.interfaces.IEnderecoGateway;
import br.com.fiapfood.core.gateways.interfaces.IImagemGateway;
import br.com.fiapfood.core.gateways.interfaces.IItemGateway;
import br.com.fiapfood.core.gateways.interfaces.ILoginGateway;
import br.com.fiapfood.core.gateways.interfaces.IPerfilGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.gateways.interfaces.ITipoCulinariaGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.usecases.atendimento.impl.AdicionarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.impl.AtualizarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.impl.ExcluirAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IAdicionarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IAtualizarAtendimentoUseCase;
import br.com.fiapfood.core.usecases.atendimento.interfaces.IExcluirAtendimentoUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarDescricaoItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarDisponibilidadeConsumoPresencialItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarDisponibilidadeItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarNomeItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.AtualizarPrecoItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.BaixarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.impl.BuscarItemPorIdUseCase;
import br.com.fiapfood.core.usecases.item.impl.BuscarTodosItensUseCase;
import br.com.fiapfood.core.usecases.item.impl.CadastrarItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDescricaoItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDisponibilidadeConsumoPresencialItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarDisponibilidadeItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarNomeItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IAtualizarPrecoItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBaixarImagemItemUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarItemPorIdUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.IBuscarTodosItensUseCase;
import br.com.fiapfood.core.usecases.item.interfaces.ICadastrarItemUseCase;
import br.com.fiapfood.core.usecases.login.impl.AtualizarMatriculaUseCase;
import br.com.fiapfood.core.usecases.login.impl.AtualizarSenhaUseCase;
import br.com.fiapfood.core.usecases.login.impl.ValidarLoginUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarMatriculaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IAtualizarSenhaUseCase;
import br.com.fiapfood.core.usecases.login.interfaces.IValidarLoginUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.AtualizarNomePerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.BuscarPerfilPorIdUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.BuscarTodosPerfisUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.CadastrarPerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.InativarPerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.impl.ReativarPerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.IAtualizarNomePerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.IBuscarPerfilPorIdUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.IBuscarTodosPerfisUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.ICadastrarPerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.IInativarPerfilUseCase;
import br.com.fiapfood.core.usecases.perfil.interfaces.IReativarPerfilUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.AtualizarDonoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.AtualizarEnderecoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.AtualizarNomeRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.AtualizarTipoCulinariaRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.BuscarRestaurantePorIdUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.BuscarTodosRestaurantesUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.CadastrarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.InativarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.impl.ReativarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarDonoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarEnderecoRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarNomeRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IAtualizarTipoCulinariaRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarRestaurantePorId;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarTodosRestaurantesUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.ICadastrarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IInativarRestauranteUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IReativarRestauranteUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.impl.AtualizarNomeTipoCulinariaUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.impl.BuscarTipoCulinariaPorIdUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.impl.BuscarTodosTiposCulinariaUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.impl.CadastrarTipoCulinariaUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.interfaces.IAtualizarNomeTipoCulinariaUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.interfaces.IBuscarTipoCulinariaPorIdUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.interfaces.IBuscarTodosTiposCulinariaUseCase;
import br.com.fiapfood.core.usecases.tipo_culinaria.interfaces.ICadastrarTipoCulinariaUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.AtualizarEmailUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.AtualizarEnderecoUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.AtualizarNomeUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.AtualizarPerfilUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.BuscarTodosUsuariosUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.BuscarUsuarioPorIdUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.CadastrarUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.InativarUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.impl.ReativarUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarEmailUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarEnderecoUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarNomeUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IAtualizarPerfilUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IBuscarTodosUsuariosUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IBuscarUsuarioPorIdUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.ICadastrarUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IInativarUsuarioUseCase;
import br.com.fiapfood.core.usecases.usuario.interfaces.IReativarUsuarioUseCase;
import br.com.fiapfood.infraestructure.repositories.interfaces.IEnderecoRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IImagemRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IItemRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.ILoginRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IPerfilRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.ITipoCulinariaRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IUsuarioRepository;

@Configuration
public class AppConfig {
	//Controllers
	
	@Bean
	public ILoginCoreController iLoginCoreController(IValidarLoginUseCase validarLoginUseCase, IAtualizarSenhaUseCase atualizarSenhaUseCase, IAtualizarMatriculaUseCase atualizarMatriculaUseCase) {
		return new LoginCoreController(validarLoginUseCase, atualizarSenhaUseCase, atualizarMatriculaUseCase);
	}
		
	@Bean
	public IPerfilCoreController iPerfilCoreController(IBuscarTodosPerfisUseCase buscarTodosUseCase, IBuscarPerfilPorIdUseCase buscarPorIdUseCase, 
													   ICadastrarPerfilUseCase cadastrarPerfilUseCase, IAtualizarNomePerfilUseCase atualizarNomePerfilUseCase,
													   IInativarPerfilUseCase inativarPerfilUseCase, IReativarPerfilUseCase reativarPerfilUseCase) {
		return new PerfilCoreController(buscarTodosUseCase, buscarPorIdUseCase, cadastrarPerfilUseCase, atualizarNomePerfilUseCase, inativarPerfilUseCase, reativarPerfilUseCase);
	}
	
	@Bean
	public IUsuarioCoreController iUsuarioCoreController(IBuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase, 
														 IBuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase,
														 ICadastrarUsuarioUseCase cadastrarUsuarioUseCase,
														 IAtualizarEmailUsuarioUseCase atualizarEmailUsuarioUseCase,
														 IAtualizarNomeUsuarioUseCase atualizarNomeUsuarioUseCase, 
														 IInativarUsuarioUseCase inativarUsuarioUseCase,
														 IReativarUsuarioUseCase reativarUsuarioUseCase,
														 IAtualizarPerfilUsuarioUseCase atualizarPerfilUsuarioUseCase,
														 IAtualizarEnderecoUsuarioUseCase atualizarEnderecoUsuarioUseCase) {
		return new UsuarioCoreController(buscarUsuarioPorIdUseCase, 
										 buscarTodosUsuariosUseCase, 
										 cadastrarUsuarioUseCase, 
										 atualizarEmailUsuarioUseCase, 
										 atualizarNomeUsuarioUseCase, 
										 inativarUsuarioUseCase,
										 reativarUsuarioUseCase,
										 atualizarPerfilUsuarioUseCase,
										 atualizarEnderecoUsuarioUseCase);
	}
	
	@Bean
	public IRestauranteCoreController restauranteCoreController(IBuscarRestaurantePorId buscarRestaurantePorId, IBuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase, 
																 ICadastrarRestauranteUseCase cadastrarRestauranteUseCase, IReativarRestauranteUseCase reativarRestauranteUseCase,
																 IInativarRestauranteUseCase inativarRestauranteUseCase, IAtualizarDonoRestauranteUseCase atualizarDonoRestauranteUseCase,
																 IAtualizarEnderecoRestauranteUseCase atualizarEnderecoRestauranteUseCase, IAtualizarNomeRestauranteUseCase atualizarNomeRestauranteUseCase,
																 IAtualizarTipoCulinariaRestauranteUseCase atualizarTipoCulinariaRestauranteUseCase,
																 IAtualizarAtendimentoUseCase atualizarAtendimentoUseCase,
																 IAdicionarAtendimentoUseCase adicionarAtendimentoUseCase, 
																 IExcluirAtendimentoUseCase excluirAtendimentoUseCase) {
		return new RestauranteCoreController(buscarRestaurantePorId, buscarTodosRestaurantesUseCase, cadastrarRestauranteUseCase, 
											 reativarRestauranteUseCase, inativarRestauranteUseCase, atualizarDonoRestauranteUseCase, atualizarEnderecoRestauranteUseCase,
											 atualizarNomeRestauranteUseCase, atualizarTipoCulinariaRestauranteUseCase,
											 atualizarAtendimentoUseCase, adicionarAtendimentoUseCase, excluirAtendimentoUseCase);
	}
	
	@Bean
	public ITipoCulinariaCoreController tipoCulinariaCoreController(IBuscarTodosTiposCulinariaUseCase buscarTodosUseCase, 
																	 IBuscarTipoCulinariaPorIdUseCase buscarPorIdUseCase,
																	 ICadastrarTipoCulinariaUseCase cadastrarTipoCulinariaUseCase, 
																	 IAtualizarNomeTipoCulinariaUseCase atualizarNomeTipoCulinariaUseCase) {
		return new TipoCulinariaCoreController(buscarTodosUseCase, buscarPorIdUseCase, cadastrarTipoCulinariaUseCase, atualizarNomeTipoCulinariaUseCase);
	}
	
	@Bean
	public IItemCoreController iItemCoreController(IBuscarItemPorIdUseCase buscarItemPorIdUseCase, IBuscarTodosItensUseCase buscarTodosItensUseCase, ICadastrarItemUseCase cadastrarItemUseCase,
												   IAtualizarDescricaoItemUseCase atualizarDescricaoItemUseCase, IAtualizarDisponibilidadeConsumoPresencialItemUseCase atualizarDisponibilidadeConsumoPresencialItemUseCase,
												   IAtualizarDisponibilidadeItemUseCase atualizarDisponibilidadeItemUseCase, IAtualizarImagemItemUseCase atualizarFotoItemUseCase,
												   IAtualizarNomeItemUseCase atualizarNomeItemUseCase, IAtualizarPrecoItemUseCase atualizarPrecoItemUseCase,
												   IBaixarImagemItemUseCase baixarImagemItemUseCase) {
		return new ItemCoreController(buscarItemPorIdUseCase, buscarTodosItensUseCase, cadastrarItemUseCase, 
									  atualizarDescricaoItemUseCase, atualizarDisponibilidadeConsumoPresencialItemUseCase, 
									  atualizarDisponibilidadeItemUseCase, atualizarFotoItemUseCase, atualizarNomeItemUseCase, 
									  atualizarPrecoItemUseCase, baixarImagemItemUseCase);
	}	
	
	// Usecases
	
	@Bean
	public IValidarLoginUseCase iValidarLoginUseCase(ILoginGateway loginGateway, IUsuarioGateway usuarioGateway) {
		return new ValidarLoginUseCase(loginGateway, usuarioGateway);
	}
	
	@Bean
	public IAtualizarMatriculaUseCase iAtualizarMatriculaUseCase(ILoginGateway loginGateway, IUsuarioGateway usuarioGateway) {
		return new AtualizarMatriculaUseCase(loginGateway, usuarioGateway);
	}
	
	@Bean
	public IAtualizarSenhaUseCase iAtualizarSenhaUseCase(ILoginGateway loginGateway, IUsuarioGateway usuarioGateway) {
		return new AtualizarSenhaUseCase(loginGateway, usuarioGateway);
	}
	
	@Bean
	public IBuscarTodosPerfisUseCase iBuscarTodosUseCase(IPerfilGateway perfilGateway) {
		return new BuscarTodosPerfisUseCase(perfilGateway);
	}
	
	@Bean
	public IBuscarPerfilPorIdUseCase iBuscarPorIdUseCase(IPerfilGateway perfilGateway) {
		return new BuscarPerfilPorIdUseCase(perfilGateway);
	}
	
	@Bean
	public IBuscarUsuarioPorIdUseCase iBuscarUsuarioPorIdUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new BuscarUsuarioPorIdUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public IBuscarTodosUsuariosUseCase iBuscarTodosUsuariosUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new BuscarTodosUsuariosUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public ICadastrarUsuarioUseCase iCadastrarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway) {
		return new CadastrarUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway);
	}
	
	@Bean
	public IAtualizarPerfilUsuarioUseCase iAtualizarPerfilUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new AtualizarPerfilUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}

	@Bean
	public IAtualizarNomeUsuarioUseCase iAtualizarNomeUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new AtualizarNomeUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public IAtualizarEmailUsuarioUseCase iAtualizarEmailUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new AtualizarEmailUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public IAtualizarEnderecoUsuarioUseCase iAtualizarEnderecoUsuarioUseCase(IUsuarioGateway usuarioGateway, IEnderecoGateway enderecoGateway) {
		return new AtualizarEnderecoUsuarioUseCase(usuarioGateway, enderecoGateway);
	}
		
	@Bean
	public IInativarUsuarioUseCase iInativarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new InativarUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public IReativarUsuarioUseCase iReativarUsuarioUseCase(IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway, ILoginGateway loginGateway, IEnderecoGateway enderecoGateway) {
		return new ReativarUsuarioUseCase(usuarioGateway, perfilGateway, loginGateway, enderecoGateway);
	}
	
	@Bean
	public IBuscarRestaurantePorId iBuscarRestaurantePorId(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IEnderecoGateway enderecoGateway, 
														   ILoginGateway loginGateway, ITipoCulinariaGateway tipoCulinariaGateway) {
		return new BuscarRestaurantePorIdUseCase(restauranteGateway, usuarioGateway, enderecoGateway, loginGateway, tipoCulinariaGateway);
	}
	
	@Bean
	public IBuscarTodosRestaurantesUseCase iBuscarTodosRestaurantesUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IEnderecoGateway enderecoGateway, 
																		   ILoginGateway loginGateway, ITipoCulinariaGateway tipoCulinariaGateway) {
		return new BuscarTodosRestaurantesUseCase(restauranteGateway, usuarioGateway, enderecoGateway, loginGateway, tipoCulinariaGateway);
	}
	
	@Bean
	public ICadastrarRestauranteUseCase iCadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		return new CadastrarRestauranteUseCase(restauranteGateway, usuarioGateway, perfilGateway);
	}
	
	@Bean
	public IReativarRestauranteUseCase iReativarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		return new ReativarRestauranteUseCase(restauranteGateway);
	}
	
	@Bean
	public IInativarRestauranteUseCase iInativarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		return new InativarRestauranteUseCase(restauranteGateway);
	}
		
	@Bean
	public IAtualizarDonoRestauranteUseCase iAtualizarDonoRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway, IPerfilGateway perfilGateway) {
		return new AtualizarDonoRestauranteUseCase(restauranteGateway, usuarioGateway, perfilGateway);
	}	
	
	@Bean
	public IAtualizarEnderecoRestauranteUseCase iAtualizarEnderecoRestauranteUseCase(IRestauranteGateway restauranteGateway, IEnderecoGateway enderecoGateway) {
		return new AtualizarEnderecoRestauranteUseCase(restauranteGateway, enderecoGateway);
	}
	
	@Bean
	public IAtualizarNomeRestauranteUseCase iAtualizarNomeRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		return new AtualizarNomeRestauranteUseCase(restauranteGateway);
	}
	
	@Bean
	public IAtualizarTipoCulinariaRestauranteUseCase iAtualizarTipoCulinariaRestauranteUseCase(IRestauranteGateway restauranteGateway) {
		return new AtualizarTipoCulinariaRestauranteUseCase(restauranteGateway);
	}

	@Bean
	public ICadastrarPerfilUseCase iCadastrarPerfilUseCase(IPerfilGateway perfilGateway) {
		return new CadastrarPerfilUseCase(perfilGateway);
	}
	
	@Bean
	public IAtualizarNomePerfilUseCase iAtualizarNomePerfilUseCase(IPerfilGateway perfilGateway) {
		return new AtualizarNomePerfilUseCase(perfilGateway);
	}
	
	@Bean
	public IInativarPerfilUseCase iInativarPerfilUseCase(IPerfilGateway perfilGateway, IUsuarioGateway usuarioGateway) {
		return new InativarPerfilUseCase(perfilGateway, usuarioGateway);
	}

	@Bean
	public IReativarPerfilUseCase iReativarPerfilUseCase(IPerfilGateway perfilGateway) {
		return new ReativarPerfilUseCase(perfilGateway);
	}
	
	@Bean
	public IBuscarTipoCulinariaPorIdUseCase iBuscarTipoCulinariaPorIdUseCase(ITipoCulinariaGateway tipoCulinariaGateway) {
		return new BuscarTipoCulinariaPorIdUseCase(tipoCulinariaGateway);
	}
	
	@Bean
	public IBuscarTodosTiposCulinariaUseCase iBuscarTodosTiposCulinariaUseCase(ITipoCulinariaGateway tipoCulinariaGateway) {
		return new BuscarTodosTiposCulinariaUseCase(tipoCulinariaGateway);
	}
	
	@Bean
	public ICadastrarTipoCulinariaUseCase iCadastrarTipoCulinariaUseCase(ITipoCulinariaGateway tipoCulinariaGateway) {
		return new CadastrarTipoCulinariaUseCase(tipoCulinariaGateway);
	}
	
	@Bean
	public IAtualizarNomeTipoCulinariaUseCase iAtualizarNomeTipoCulinariaUseCase(ITipoCulinariaGateway tipoCulinariaGateway) {
		return new AtualizarNomeTipoCulinariaUseCase(tipoCulinariaGateway);
	}
	
	@Bean
	public IBuscarItemPorIdUseCase iBuscarItemPorIdUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		return new BuscarItemPorIdUseCase(itemGateway, restauranteGateway);
	}
	
	@Bean
	public IBuscarTodosItensUseCase iBuscarTodosItensUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		return new BuscarTodosItensUseCase(itemGateway, restauranteGateway);
	}
	
	@Bean
	public ICadastrarItemUseCase iCadastrarItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway) {
		return new CadastrarItemUseCase(itemGateway, restauranteGateway);
	}
	
	@Bean
	public IAtualizarDescricaoItemUseCase iAtualizarDescricaoItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway,  IImagemGateway imagemGateway) {
		return new AtualizarDescricaoItemUseCase(itemGateway, restauranteGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarDisponibilidadeConsumoPresencialItemUseCase iAtualizarDisponibilidadeConsumoPresencialItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		return new AtualizarDisponibilidadeConsumoPresencialItemUseCase(itemGateway, restauranteGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarDisponibilidadeItemUseCase iAtualizarDisponibilidadeItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		return new AtualizarDisponibilidadeItemUseCase(itemGateway, restauranteGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarImagemItemUseCase iAtualizarImagemItemUseCase(IItemGateway itemGateway, IImagemGateway imagemGateway) {
		return new AtualizarImagemItemUseCase(itemGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarPrecoItemUseCase iAtualizarPrecoItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		return new AtualizarPrecoItemUseCase(itemGateway, restauranteGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarNomeItemUseCase iAtualizarNomeItemUseCase(IItemGateway itemGateway, IRestauranteGateway restauranteGateway, IImagemGateway imagemGateway) {
		return new AtualizarNomeItemUseCase(itemGateway, restauranteGateway, imagemGateway);
	}
	
	@Bean
	public IBaixarImagemItemUseCase iBaixarImagemItemUseCase(IItemGateway itemGateway, IImagemGateway imagemGateway) {
		return new BaixarImagemItemUseCase(itemGateway, imagemGateway);
	}
	
	@Bean
	public IAtualizarAtendimentoUseCase iAtualizarAtendimentoUseCase(IRestauranteGateway restauranteGateway) {
		return new AtualizarAtendimentoUseCase(restauranteGateway);
	}

	@Bean
	public IAdicionarAtendimentoUseCase iAdicionarAtendimentoUseCase(IRestauranteGateway restauranteGateway) {
		return new AdicionarAtendimentoUseCase(restauranteGateway);
	}
	
	@Bean
	public IExcluirAtendimentoUseCase iExcluirAtendimentoUseCase(IRestauranteGateway restauranteGateway) {
		return new ExcluirAtendimentoUseCase(restauranteGateway);
	}
	
	// Gateways
	
	@Bean
	public ILoginGateway iLoginGateway(ILoginRepository loginRepository) {
		return new LoginGateway(loginRepository);
	}
	
	@Bean
	public IUsuarioGateway iUsuarioGateway(IUsuarioRepository usuarioRepository) {
		return new UsuarioGateway(usuarioRepository);
	}
	
	@Bean
	public IPerfilGateway iPerfilGateway(IPerfilRepository perfilRepository) {
		return new PerfilGateway(perfilRepository);
	}
	
	@Bean
	public IEnderecoGateway iEnderecoGateway(IEnderecoRepository enderecoRepository) {
		return new EnderecoGateway(enderecoRepository);
	}
	
	@Bean
	public IRestauranteGateway iRestauranteGateway(IRestauranteRepository restauranteRepository) {
		return new RestauranteGateway(restauranteRepository);
	}
	
	@Bean
	public ITipoCulinariaGateway iTipoCulinariaGateway(ITipoCulinariaRepository tipoCulinariaRepository) {
		return new TipoCulinariaGateway(tipoCulinariaRepository);
	}

	@Bean
	public IItemGateway iItemGateway(IItemRepository itemRepository) {
		return new ItemGateway(itemRepository);
	}

	@Bean
	public IImagemGateway iImagemGateway(IImagemRepository imagemRepository) {
		return new ImagemGateway(imagemRepository);
	}
}