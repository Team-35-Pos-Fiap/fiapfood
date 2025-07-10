package br.com.fiapfood.infraestructure.controllers;

import br.com.fiapfood.core.controllers.interfaces.ICardapioCoreController;
import br.com.fiapfood.core.entities.dto.CardapioDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioComPaginacaoDto;
import br.com.fiapfood.core.entities.dto.DadosCardapioDto;
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
@RequestMapping("/cardapios")
@Slf4j
public class CardapioController {

    private final ICardapioCoreController cardapioCoreController;

    public CardapioController(ICardapioCoreController cardapioCoreController) {
        this.cardapioCoreController = cardapioCoreController;
    }

    @GetMapping
    public ResponseEntity<DadosCardapioComPaginacaoDto> buscarCardapios(@RequestParam(defaultValue = "1") final Integer pagina) {
        log.info("buscarCardapios() - pagina {}", pagina);

        return ResponseEntity.ok().body(cardapioCoreController.buscarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardapioDto> buscarCardapioPorid(@PathVariable @NotNull @Valid UUID id) {
        log.info("buscarCardapioPorid():id {}", id);

        return ResponseEntity.ok().body(cardapioCoreController.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull DadosCardapioDto cardapio) {
        log.info("cadastrar():cardapios {}", cardapio);

        cardapioCoreController.cadastrar(cardapio);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizar(@PathVariable @NotNull @Valid UUID id, @RequestBody @Valid @NotNull DadosCardapioDto request) {
        log.info("atualizar():cardapios {}", id);
        cardapioCoreController.atualizar(id, request);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_ATUALIZACAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletar(@Valid @PathVariable @NotNull UUID id) {
        log.info("deletar():cardapio(id) {}", id);

        cardapioCoreController.deletarCardapio(id);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_DELECAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

}
