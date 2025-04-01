package com.controle.estoque.application.controllers;

import com.controle.estoque.application.service.ProductService;
import com.controle.estoque.dto.request.ProductRequest;
import com.controle.estoque.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Transactional
@RestController
@RequestMapping("produtos")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> cadastrar(@RequestBody @Valid ProductRequest produtoRequest,
                                                     UriComponentsBuilder uriComponentsBuilder) {

        ProductResponse reponse = service.cadastrar(produtoRequest);

        var uri = uriComponentsBuilder.path("/produtos/{id}").buildAndExpand(reponse.getId()).toUri();

        return ResponseEntity.created(uri).body(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable pageable) {
        Page<ProductResponse> produtos = service.listarDisponiveis(pageable);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ProductResponse> buscar(@RequestParam String nome) {
        return service.buscarPeloNome(nome)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/repor")
    public ResponseEntity<ProductResponse> repor(@PathVariable Long id, @RequestParam int quantidade) {
         ProductResponse response = service.reporEstoque(id, quantidade);
         return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/vender")
    public ResponseEntity<ProductResponse> vender(@PathVariable Long id, @RequestParam int quantidade) {
        ProductResponse response = service.venderProduto(id, quantidade);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> atualizar(@PathVariable Long id, @RequestBody ProductRequest produtoRequest){
        ProductResponse response = service.atualizar(id, produtoRequest);
         return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
