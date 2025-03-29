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
    public ResponseEntity<ProdutoResponse> cadastrar(@RequestBody @Valid ProdutoRequest produtoRequest) {
        ProdutoResponse reponse = service.cadastrar(produtoRequest);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping
    public List<ProdutoResponse> listar() {
        return service.listarDisponiveis();
    }

    @GetMapping("/buscar")
    public ProdutoResponse buscar(@RequestParam String nome) {
        return service.buscarPeloNome(nome);
    }

    @PutMapping("/{id}/repor")
    public ProdutoResponse repor(@PathVariable Long id, @RequestParam int quantidade) {
         return service.reporEstoque(id, quantidade);
    }

    @PutMapping("/{id}/vender")
    public ProdutoResponse vender(@PathVariable Long id, @RequestParam int quantidade) {
        return service.venderProduto(id, quantidade);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest){
        return service.atualizar(id, produtoRequest);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
