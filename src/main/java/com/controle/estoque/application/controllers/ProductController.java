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
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest,
                                                  UriComponentsBuilder uriComponentsBuilder) {

        ProductResponse reponse = service.create(productRequest);

        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(reponse.getId()).toUri();

        return ResponseEntity.created(uri).body(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(@PageableDefault(size = 20, sort = {"nome"}) Pageable pageable) {
        Page<ProductResponse> products = service.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/find")
    public ResponseEntity<ProductResponse> findByName(@RequestParam String name) {
        return service.findByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/restock")
    public ResponseEntity<ProductResponse> restock(@PathVariable Long id, @RequestParam int quantity) {
         ProductResponse response = service.restock(id, quantity);
         return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<ProductResponse> sell(@PathVariable Long id, @RequestParam int quantity) {
        ProductResponse response = service.sell(id, quantity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        ProductResponse response = service.update(id, productRequest);
         return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
