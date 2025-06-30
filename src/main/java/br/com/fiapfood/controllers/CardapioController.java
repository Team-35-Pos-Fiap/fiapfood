package br.com.fiapfood.controllers;

import br.com.fiapfood.controllers.response.MensagemResponse;
import br.com.fiapfood.controllers.response.SucessoResponse;
import br.com.fiapfood.entities.record.request.CardapioRecordRequest;
import br.com.fiapfood.entities.record.request.UsuarioRecordRequest;
import br.com.fiapfood.entities.record.response.CardapioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.CardapioRecordResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordPaginacaoResponse;
import br.com.fiapfood.entities.record.response.UsuarioRecordResponse;
import br.com.fiapfood.services.interfaces.ICardapioService;
import br.com.fiapfood.utils.MensagensUtil;
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

    private final ICardapioService cardapioService;

    public CardapioController(ICardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping
    public ResponseEntity<CardapioRecordPaginacaoResponse> buscarCardapios(@RequestParam(defaultValue = "1") final Integer pagina) {
        log.info("buscarCardapios() - pagina {}", pagina);

        return ResponseEntity.ok().body(cardapioService.buscarTodos(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardapioRecordResponse> buscarCardapioPorid(@PathVariable @NotNull @Valid UUID id) {
        log.info("buscarCardapioPorid():id {}", id);

        return ResponseEntity.ok().body(cardapioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody @NotNull CardapioRecordRequest cardapio) {
        log.info("cadastrar():cardapios {}", cardapio);

        cardapioService.cadastrar(cardapio);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizar(@PathVariable @NotNull @Valid UUID id, @RequestBody @Valid @NotNull CardapioRecordRequest request) {
        log.info("atualizar():cardapios {}", id);
        cardapioService.atualizar(id, request);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_ATUALIZACAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletar(@Valid @PathVariable @NotNull UUID id) {
        log.info("deletar():cardapio(id) {}", id);

        cardapioService.deletarCardapio(id);

        MensagemResponse sucessoResponse = new SucessoResponse(MensagensUtil.recuperarMensagem(MensagensUtil.SUCESSO_DELECAO_CARDAPIO));
        return ResponseEntity.ok(sucessoResponse);
    }

}
