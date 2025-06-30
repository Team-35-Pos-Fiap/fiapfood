package br.com.fiapfood.controllers;

import br.com.fiapfood.controllers.response.MensagemResponse;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.EnderecoRecordRequest;
import br.com.fiapfood.entities.record.request.RestauranteRecordRequest;
import br.com.fiapfood.entities.record.response.RestauranteRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.RestauranteRecordResponse;
import br.com.fiapfood.services.interfaces.IRestauranteService;
import br.com.fiapfood.utils.MensagensUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurantes")
@Slf4j
public class RestauranteController {

    private final IRestauranteService restauranteService;

    public RestauranteController(IRestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public ResponseEntity<RestauranteRecordPaginacaoResponse> buscarRestaurantes(@RequestParam(defaultValue = "1") final Integer pagina) {
        log.info("buscarRestaurantes() - pagina {}", pagina);

        return ResponseEntity.ok().body(restauranteService.buscarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteRecordResponse> buscarRestaurantePorid(@PathVariable @NotNull @Valid UUID id) {
        log.info("buscarRestaurantePorid():id {}", id);

        return ResponseEntity.ok().body(restauranteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull RestauranteRecordRequest restaurante) {
        log.info("cadastrar():restaurantes {}", restaurante);

        restauranteService.cadastrar(restaurante);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizar(@PathVariable @NotNull @Valid UUID id, @RequestBody @Valid @NotNull RestauranteRecordRequest request) {
        log.info("cadastrar():restaurantes {}", id);
        restauranteService.atualizarRestaurante(id, request);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_ATUALIZACAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

    @PatchMapping("/{id}/endereco")
    public ResponseEntity<Void> atualizarEndereco(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull EnderecoRecordRequest dadosEndereco) {
        restauranteService.atualizarEnderecoRestaurante(id, dadosEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletar(@Valid @PathVariable @NotNull UUID id) {
        log.info("deletar():restaurante(id) {}", id);

        restauranteService.deletarRestaurante(id);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_DELECAO_RESTAURANTE));
        return ResponseEntity.ok(sucessoResponse);
    }

}
