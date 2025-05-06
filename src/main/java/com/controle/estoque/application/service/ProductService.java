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
import com.controle.estoque.adapters.dto.productDTO.ProductRequest;
import com.controle.estoque.adapters.dto.productDTO.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final StockMovementRepository stockMovementRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public ProductResponse create(ProductRequest request) {

        repository.findByNameAndCategoryAndPrice(
                request.getName(),
                request.getCategory(),
                request.getPrice()
        ).ifPresent(produto -> {
            throw new ProductAlreadyExistsException("The product already exists with the same data provided.");
        });

        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<ProductResponse> response = repository.findByAvailableQuantityGreaterThan(0, pageable)
                .map(mapper::toResponse);

        if (response.isEmpty()) {
            throw new ProductNotFoundException("No product found!");
        }

        return response;
    }

    @Transactional(readOnly = true)
    public ProductResponse findByName(String name) {
       return repository.findByNameContainingIgnoreCase(name)
               .map(mapper::toResponse)
               .orElseThrow(() -> new ProductNotFoundException("No product found!"));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found!"));

        mapper.toEntity(productRequest);
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setAvailableQuantity(productRequest.getAvailableQuantity());

        return mapper.toResponse(repository.save(product));
    }

    @Transactional
    public ProductResponse restock(Long id, int quantity) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("No product found!");
        }
        if (quantity <= 0 ) {
            throw new InvalidQuantityException("The value must be greater than zero!");
        }

        Product prod = product.get();
        prod.setAvailableQuantity(prod.getAvailableQuantity() + quantity);
        repository.save(prod);

        StockMovement stockMovement = new StockMovement(prod, quantity, MovementType.ENTRADA);
        stockMovementRepository.save(stockMovement);

        return mapper.toResponse(prod);
    }

    @Transactional
    public ProductResponse sell(Long id, int quantitySold) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found!"));

        if (quantitySold <= 0) {
            throw new InvalidQuantityException("The value must be greater than zero!");
        }

        if (product.getAvailableQuantity() < quantitySold) {
            throw new InsufficientStockException("Insufficient stock!");
        }

        product.setAvailableQuantity(product.getAvailableQuantity() - quantitySold);
        repository.save(product);

        Sale sale = new Sale(quantitySold, product);
        saleRepository.save(sale);

        StockMovement stockMovement = new StockMovement(product, quantitySold, MovementType.SAIDA);
        stockMovementRepository.save(stockMovement);

        return mapper.toResponse(product);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("No product found!");
        }
     repository.deleteById(id);
    }
}
