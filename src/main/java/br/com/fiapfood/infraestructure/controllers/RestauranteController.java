package br.com.fiapfood.infraestructure.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.infraestructure.controllers.request.atendimento.AtendimentoDto;
import br.com.fiapfood.infraestructure.controllers.request.endereco.DadosEnderecoDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.CadastrarRestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosDonoDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.DadosTipoCulinariaDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.NomeDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestauranteDto;
import br.com.fiapfood.infraestructure.controllers.request.restaurante.RestaurantePaginacaoDto;
import br.com.fiapfood.infraestructure.controllers.response.MensagemResponse;
import br.com.fiapfood.infraestructure.controllers.response.SucessoResponse;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    private final IRestauranteCoreController restauranteCoreController;

    public RestauranteController(IRestauranteCoreController restauranteCoreController) {
        this.restauranteCoreController = restauranteCoreController;
    }

    @GetMapping
    public ResponseEntity<RestaurantePaginacaoDto> buscarRestaurantes(@RequestParam(defaultValue = "1") @Valid @Positive(message = "O parâmetro página precisa ser maior do que 0.") 
    																  final Integer pagina) {
        log.info("buscarRestaurantes() - pagina {}", pagina);

        return ResponseEntity.ok().body(restauranteCoreController.buscarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDto> buscarRestaurantePorid(@PathVariable @NotNull @Valid final UUID id) {
        log.info("buscarRestaurantePorid():id {}", id);

        return ResponseEntity.ok().body(restauranteCoreController.buscarPorId(id));
    }
 
    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull final CadastrarRestauranteDto restaurante) {
        log.info("cadastrar() - dados do restaurante: {}", restaurante);

        restauranteCoreController.cadastrar(restaurante);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PatchMapping("/{id}/status/inativa")
	public ResponseEntity<MensagemResponse> inativar(@Valid @PathVariable @NotNull final UUID id) {
		log.info("inativar():id {}", id);

		restauranteCoreController.inativar(id);

		MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_INATIVACAO_RESTAURANTE));
		
		return ResponseEntity.ok(sucessoResponse);
	}
	
	@PatchMapping("/{id}/status/reativa")
	public ResponseEntity<MensagemResponse> reativar(@Valid @PathVariable @NotNull final UUID id) {
		log.info("reativar():id {}", id);

		restauranteCoreController.reativar(id);
		
		MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_REATIVACAO_RESTAURANTE));
		
		return ResponseEntity.ok(sucessoResponse);
	} 

    @PatchMapping("/{id}/tipo-culinaria")
    public ResponseEntity<MensagemResponse> atualizarTipoCulinaria(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final DadosTipoCulinariaDto dados) {
        log.info("atualizarTipoCulinaria(): id {} - idTipoCulinaria: {}", id, dados.idTipoCulinaria());
        
        restauranteCoreController.atualizarTipoCulinaria(id, dados.idTipoCulinaria());
        
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    

    @PatchMapping("/{id}/dono")
    public ResponseEntity<MensagemResponse> atualizarDono(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final DadosDonoDto dados) {
        log.info("atualizarDono(): id {} - idDono: {}", id, dados.idDono());
        
        restauranteCoreController.atualizarDono(id, dados.idDono());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    

    @PatchMapping("/{id}/nome")
    public ResponseEntity<MensagemResponse> atualizarNome(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final NomeDto dados) {
        log.info("atualizarNome(): id {} - nome: {}", id, dados.nome());
        
        restauranteCoreController.atualizarNome(id, dados.nome());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @PatchMapping("/{id}/endereco")
    public ResponseEntity<MensagemResponse> atualizarEndereco(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final DadosEnderecoDto dadosEndereco) {
        log.info("atualizarEndereco(): id {} - dados do endereço: {}", id, dadosEndereco);
        
        restauranteCoreController.atualizarEndereco(id, dadosEndereco);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @PatchMapping("/{id}/atendimento")
    public ResponseEntity<MensagemResponse> atualizarAtendimento(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final AtendimentoDto atendimento) {
        log.info("atualizarAtendimento(): id {} - dados do atendimento: {}", id, atendimento);
        
        restauranteCoreController.atualizarAtendimento(id, atendimento);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @PostMapping("/{id}/atendimento")
    public ResponseEntity<MensagemResponse> adicionarAtendimento(@PathVariable @NotNull @Valid final UUID id, @RequestBody @Valid @NotNull final AtendimentoDto atendimento) {
        log.info("adicionarAtendimento(): id {} - dados do atendimento: {}", id, atendimento);
        
        restauranteCoreController.adicionarAtendimento(id, atendimento);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
    
    @DeleteMapping("/{id-restaurante}/atendimento/{id-atendimento}")
    public ResponseEntity<MensagemResponse> excluirAtendimento(@PathVariable(name = "id-restaurante") @NotNull @Valid final UUID idRestaurante, 
    														   @PathVariable(name = "id-atendimento") @NotNull @Valid final UUID idAtendimento) {
        log.info("excluirAtendimento(): id {} - dados do atendimento: {}", idRestaurante, idAtendimento);
        
        restauranteCoreController.excluirAtendimento(idRestaurante, idAtendimento);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
    }
}
