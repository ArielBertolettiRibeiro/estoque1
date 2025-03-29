package com.controle.estoque.application.controllers;

import com.controle.estoque.application.service.ProdutoService;
import com.controle.estoque.dto.request.ProdutoRequest;
import com.controle.estoque.dto.response.ProdutoResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Transactional
@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@RequestBody @Valid ProdutoRequest produtoRequest,
                                                     UriComponentsBuilder uriComponentsBuilder) {

        ProdutoResponse reponse = service.cadastrar(produtoRequest);

        var uri = uriComponentsBuilder.path("/produtos/{id}").buildAndExpand(reponse.getId()).toUri();

        return ResponseEntity.created(uri).body(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable pageable) {
        Page<ProdutoResponse> produtos = service.listarDisponiveis(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ProdutoResponse> buscar(@RequestParam String nome) {
        return service.buscarPeloNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/repor")
    public ResponseEntity<ProdutoResponse> repor(@PathVariable Long id, @RequestParam int quantidade) {
         ProdutoResponse response = service.reporEstoque(id, quantidade);
         return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/vender")
    public ResponseEntity<ProdutoResponse> vender(@PathVariable Long id, @RequestParam int quantidade) {
        ProdutoResponse response = service.venderProduto(id, quantidade);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest){
        ProdutoResponse response = service.atualizar(id, produtoRequest);
         return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
