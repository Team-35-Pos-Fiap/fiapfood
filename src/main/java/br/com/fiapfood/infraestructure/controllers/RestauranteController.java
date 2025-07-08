package br.com.fiapfood.infraestructure.controllers;


import br.com.fiapfood.core.controllers.interfaces.IRestauranteCoreController;
import br.com.fiapfood.core.entities.dto.DadosEnderecoDto;
import br.com.fiapfood.core.entities.dto.DadosRestauranteComPaginacaoDto;
import br.com.fiapfood.core.entities.dto.DadosRestauranteDto;
import br.com.fiapfood.core.entities.dto.RestauranteDto;
import br.com.fiapfood.infraestructure.controllers.response.MensagemResponse;
import br.com.fiapfood.infraestructure.controllers.response.SucessoResponse;
import br.com.fiapfood.infraestructure.utils.MensagensUtil;
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

    private final IRestauranteCoreController restauranteCoreController;

    public RestauranteController(IRestauranteCoreController restauranteCoreController) {
        this.restauranteCoreController = restauranteCoreController;
    }

    @GetMapping
    public ResponseEntity<DadosRestauranteComPaginacaoDto> buscarRestaurantes(@RequestParam(defaultValue = "1") final Integer pagina) {
        log.info("buscarRestaurantes() - pagina {}", pagina);

        return ResponseEntity.ok().body(restauranteCoreController.buscarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDto> buscarRestaurantePorid(@PathVariable @NotNull @Valid UUID id) {
        log.info("buscarRestaurantePorid():id {}", id);

        return ResponseEntity.ok().body(restauranteCoreController.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull DadosRestauranteDto restaurante) {
        log.info("cadastrar():restaurantes {}", restaurante);

        restauranteCoreController.cadastrar(restaurante);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizar(@PathVariable @NotNull @Valid UUID id, @RequestBody @Valid @NotNull DadosRestauranteDto request) {
        log.info("cadastrar():restaurantes {}", id);
        restauranteCoreController.atualizarRestaurante(id, request);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_ATUALIZACAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

    @PatchMapping("/{id}/endereco")
    public ResponseEntity<Void> atualizarEndereco(@Valid @PathVariable @NotNull UUID id, @Valid @RequestBody @NotNull DadosEnderecoDto dadosEndereco) {
        restauranteCoreController.atualizarEnderecoRestaurante(id, dadosEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletar(@Valid @PathVariable @NotNull UUID id) {
        log.info("deletar():restaurante(id) {}", id);

        restauranteCoreController.deletarRestaurante(id);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_DELECAO_RESTAURANTE));
        return ResponseEntity.ok(sucessoResponse);
    }

}
