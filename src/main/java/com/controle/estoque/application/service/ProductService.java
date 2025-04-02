package com.controle.estoque.application.service;

import com.controle.estoque.config.mapper.ProductMapper;
import com.controle.estoque.infrastructure.repository.StockMovementRepository;
import com.controle.estoque.infrastructure.repository.ProductRepository;
import com.controle.estoque.infrastructure.repository.SaleRepository;
import com.controle.estoque.domain.entities.StockMovement;
import com.controle.estoque.domain.entities.Product;
import com.controle.estoque.domain.enums.MovementType;
import com.controle.estoque.domain.entities.Sale;
import com.controle.estoque.dto.request.ProductRequest;
import com.controle.estoque.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class ProductService {

    private ProductMapper mapper;
    private ProductRepository repository;
    private StockMovementRepository stockMovementRepository;
    private SaleRepository saleRepository;

    public ProductService(ProductMapper mapper, ProductRepository repository, StockMovementRepository stockMovementRepository, SaleRepository saleRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.stockMovementRepository = stockMovementRepository;
        this.saleRepository = saleRepository;
    }

    public ProductResponse create(ProductRequest request) {
        Product produto = mapper.toEntity(request);
        Product produtoSalvo = repository.save(produto);

        return mapper.toResponse(produtoSalvo);
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        return repository.findByQuantidadeDisponivelGreaterThan(0, pageable)
                .map(mapper::toResponse);
    }

    public Optional<ProductResponse> findByName(String nome) {
       return repository.findByNomeContainingIgnoreCase(nome)
               .map(mapper::toResponse);
    }

    public ProductResponse update(Long id, ProductRequest produtoRequest) {
        Product produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        mapper.toEntity(produtoRequest);
        produto.setNome(produtoRequest.getNome());
        produto.setCategoria(produtoRequest.getCategoria());
        produto.setPreco(produtoRequest.getPreco());
        produto.setQuantidadeDisponivel(produtoRequest.getQuantidadeDisponivel());

        Product atualizado = repository.save(produto);
        return mapper.toResponse(atualizado);
    }

    public ProductResponse restock(Long id, int quantidade) {
        Optional<Product> produto = repository.findById(id);
        if (produto.isEmpty()) {
            throw new IllegalArgumentException("produto não encontrado!");
        }
        if (quantidade <= 0 ) {
            throw new IllegalArgumentException("Quantidade adicional deve ser maior que zero!");
        }

        Product prod = produto.get();
        prod.setQuantidadeDisponivel(prod.getQuantidadeDisponivel() + quantidade);
        repository.save(prod);

        StockMovement movimentacao = new StockMovement(prod, quantidade, MovementType.ENTRADA);
        stockMovementRepository.save(movimentacao);

        return mapper.toResponse(prod);
    }

    public ProductResponse sell(Long id, int quantidadeVendida) {
        Product produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        if (quantidadeVendida <= 0) {
            throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero!");
        }

        if (produto.getQuantidadeDisponivel() < quantidadeVendida) {
            throw new IllegalArgumentException("Estoque insuficiente para venda!");
        }

        produto.setQuantidadeDisponivel(produto.getQuantidadeDisponivel() - quantidadeVendida);
        repository.save(produto);

        Sale venda = new Sale(quantidadeVendida, produto);
        saleRepository.save(venda);

        StockMovement movimentacaoEstoque = new StockMovement(produto, quantidadeVendida, MovementType.SAIDA);
        stockMovementRepository.save(movimentacaoEstoque);

        return mapper.toResponse(produto);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado!");
        }
     repository.deleteById(id);
    }
}
