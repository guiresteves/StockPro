package com.br.stockpro.service;

import com.br.stockpro.dtos.produto.ProdutoListDTO;
import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.exceptions.BusinessException;
import com.br.stockpro.exceptions.NotFoundException;
import com.br.stockpro.mapper.ProdutoMapper;
import com.br.stockpro.model.Produto;
import com.br.stockpro.repository.CategoriaRepository;
import com.br.stockpro.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoResponseDTO create(ProdutoRequestDTO dto) {

        validarCodigoBarras(dto.codigoBarras());
        validarRegraDeValidade(dto.temValidade(), dto.dataValidade());

        var categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + dto.categoriaId()));

        Produto produto = produtoMapper.toEntity(dto);

        if (!dto.temValidade()) {
            produto.setDataValidade(null);
        }

        produto.setCategoria(categoria);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(salvo);
    }

    public ProdutoResponseDTO update(UUID id, ProdutoRequestDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        if (produtoRepository.existsByCodigoBarrasAndIdNot(dto.codigoBarras(), id)) {
            throw new BusinessException("Já existe um produto com esse código de barras.");
        }

        validarRegraDeValidade(dto.temValidade(), dto.dataValidade());

        var categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + dto.categoriaId()));

        produtoMapper.updateEntityFromDTO(dto, existente);

        if (!dto.temValidade()) {
            existente.setDataValidade(null);
        }

        existente.setCategoria(categoria);
        Produto atualizado = produtoRepository.save(existente);
        return produtoMapper.toResponseDTO(atualizado);
    }

    public ProdutoResponseDTO findById(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        return produtoMapper.toResponseDTO(produto);
    }

    public List<ProdutoListDTO> findAll() {
        return produtoMapper.toListDTO(produtoRepository.findAll());
    }

    public void delete(UUID id) {
       Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));

        produtoRepository.delete(produto);
    }

    public ProdutoResponseDTO findByCodigoBarras(String codigo) {
        Produto produto = produtoRepository.findByCodigoBarras(codigo)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado com código de barras: " + codigo));

        return produtoMapper.toResponseDTO(produto);
    }

    public List<ProdutoListDTO> listarComEstoqueBaixo() {
        List<Produto> produtos = produtoRepository.findProdutosAbaixoDoEstoqueMinimo();
        return produtoMapper.toListDTO(produtos);
    }

    public List<ProdutoListDTO> listarAtivos() {
        return produtoMapper.toListDTO(
                produtoRepository.findByAtivoTrue()
        );
    }

    public List<ProdutoListDTO> listarComValidade() {
        return produtoMapper.toListDTO(
                produtoRepository.findByTemValidadeTrue()
        );
    }

    public List<ProdutoListDTO> listarVencidos() {
        LocalDate hoje = LocalDate.now();
        return produtoMapper.toListDTO(
                produtoRepository.findByDataValidadeBefore(hoje)
        );
    }

    public List<ProdutoListDTO> listarVencendoAntes(LocalDate data) {
        return produtoMapper.toListDTO(
                produtoRepository.findByDataValidadeBefore(data)
        );
    }


    private void validarCodigoBarras(String codigoBarras) {
        if (produtoRepository.existsByCodigoBarras(codigoBarras)) {
            throw new BusinessException("Já existe um produto com o código de barras informado.");
        }
    }

    private void validarRegraDeValidade(boolean temValidade, java.time.LocalDate dataValidade) {
        if (temValidade && dataValidade == null) {
            throw new BusinessException(
                    "Data de validade é obrigatória quando 'temValidade' é verdadeiro."
            );
        }

        if (!temValidade && dataValidade != null) {
            throw new BusinessException("O produto não possui validade, remova a data de validade.");
        }
    }
}
