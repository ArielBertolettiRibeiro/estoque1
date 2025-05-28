package com.controle.estoque.adapters.controllers;

import com.controle.estoque.application.service.ProductService;
import com.controle.estoque.adapters.dto.productDTO.ProductRequest;
import com.controle.estoque.adapters.dto.productDTO.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest, UriComponentsBuilder uriComponentsBuilder) {
        ProductResponse reponse = service.create(productRequest);

        var uri = uriComponentsBuilder.path("/products/{id}").buildAndExpand(reponse.getId()).toUri();

        return ResponseEntity.created(uri).body(reponse);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> findAll(@PageableDefault(size = 20, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/find")
    public ResponseEntity<ProductResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @PutMapping("/{id}/restock")
    public ResponseEntity<ProductResponse> restock(@PathVariable Long id, @RequestParam int quantity) {
         return ResponseEntity.ok(service.restock(id, quantity));
    }

    @PutMapping("/{id}/sell")
    public ResponseEntity<ProductResponse> sell(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(service.sell(id, quantity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest productRequest){
         return ResponseEntity.ok(service.update(id, productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
