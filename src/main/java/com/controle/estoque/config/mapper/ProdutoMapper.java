package com.controle.estoque.config.mapper;

import com.controle.estoque.domain.entities.Produto;
import com.controle.estoque.dto.request.ProdutoRequest;
import com.controle.estoque.dto.response.ProdutoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    private final ModelMapper modelMapper;

    public ProdutoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoRequest toRequest(Produto produto) {
        return modelMapper.map(produto, ProdutoRequest.class);
    }

    public Produto toEntity(ProdutoRequest produtoRequest) {
        return modelMapper.map(produtoRequest, Produto.class);
    }

    public ProdutoResponse toResponse(Produto produto) {
        return modelMapper.map(produto, ProdutoResponse.class);
    }

    public List<ProdutoResponse> toResponseList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
