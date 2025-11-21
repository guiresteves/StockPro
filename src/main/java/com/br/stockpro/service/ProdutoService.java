package com.br.stockpro.service;

import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.exceptions.NotFoundException;
import com.br.stockpro.mapper.ProdutoMapper;
import com.br.stockpro.model.Produto;
import com.br.stockpro.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoResponseDTO create(ProdutoRequestDTO dto) {
        Produto produto = produtoMapper.toEntity(dto);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(salvo);
    }

    public ProdutoResponseDTO update(UUID id, ProdutoRequestDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        produtoMapper.updateEntityFromDTO(dto, existente);

        Produto atualizado = produtoRepository.save(existente);
        return produtoMapper.toResponseDTO(atualizado);
    }

    public ProdutoResponseDTO findById(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        return produtoMapper.toResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> findAll() {
        return produtoMapper.toListDTO(produtoRepository.findAll());
    }

    public void delete(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        produtoRepository.delete(produto);
    }
}
