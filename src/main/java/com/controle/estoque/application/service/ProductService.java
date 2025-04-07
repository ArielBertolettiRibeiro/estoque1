package com.controle.estoque.application.service;

import com.controle.estoque.application.exceptions.InvalidQuantityException;
import com.controle.estoque.application.exceptions.ProductAlreadyExistsException;
import com.controle.estoque.application.exceptions.ProductNotFoundException;
import com.controle.estoque.application.exceptions.InsufficientStockException;
import com.controle.estoque.infrastructure.config.mapper.ProductMapper;
import com.controle.estoque.infrastructure.repository.StockMovementRepository;
import com.controle.estoque.infrastructure.repository.ProductRepository;
import com.controle.estoque.infrastructure.repository.SaleRepository;
import com.controle.estoque.domain.entities.StockMovement;
import com.controle.estoque.domain.entities.Product;
import com.controle.estoque.domain.enums.MovementType;
import com.controle.estoque.domain.entities.Sale;
import com.controle.estoque.adapters.dto.requestDTO.ProductRequest;
import com.controle.estoque.adapters.dto.responseDTO.ProductResponse;
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

        repository.findByNomeAndCategoriaAndPrecoAndQuantidadeDisponivel(
                request.getNome(),
                request.getCategoria(),
                request.getPreco(),
                request.getQuantidadeDisponivel()
        ).ifPresent(produto -> {
            throw new ProductAlreadyExistsException("The product already exists with the same data provided.");
        });

        Product produto = mapper.toEntity(request);
        Product produtoSalvo = repository.save(produto);

        return mapper.toResponse(produtoSalvo);
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<ProductResponse> response = repository.findByQuantidadeDisponivelGreaterThan(0, pageable)
                .map(mapper::toResponse);

        if (response.isEmpty()) {
            throw new ProductNotFoundException("No product found!");
        }

        return response;
    }

    public ProductResponse findByName(String nome) {
       return repository.findByNomeContainingIgnoreCase(nome)
               .map(mapper::toResponse)
               .orElseThrow(() -> new ProductNotFoundException("No product found!"));
    }

    public ProductResponse update(Long id, ProductRequest produtoRequest) {
        Product produto = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found!"));

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
            throw new ProductNotFoundException("No product found!");
        }
        if (quantidade <= 0 ) {
            throw new InvalidQuantityException("The value must be greater than zero!");
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
                .orElseThrow(() -> new ProductNotFoundException("No product found!"));

        if (quantidadeVendida <= 0) {
            throw new InvalidQuantityException("The value must be greater than zero!");
        }

        if (produto.getQuantidadeDisponivel() < quantidadeVendida) {
            throw new InsufficientStockException("Insufficient stock!");
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
            throw new ProductNotFoundException("No product found!");
        }
     repository.deleteById(id);
    }
}
