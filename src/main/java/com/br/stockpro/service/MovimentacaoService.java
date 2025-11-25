package com.br.stockpro.service;

import com.br.stockpro.dtos.movimentacao.MovimentacaoRequestDTO;
import com.br.stockpro.dtos.movimentacao.MovimentacaoResponseDTO;
import com.br.stockpro.enums.TipoMovimentacao;
import com.br.stockpro.exceptions.NotFoundException;
import com.br.stockpro.mapper.MovimentacaoMapper;
import com.br.stockpro.model.Movimentacao;
import com.br.stockpro.model.Produto;
import com.br.stockpro.repository.MovimentacaoRepository;
import com.br.stockpro.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoMapper movimentacaoMapper;


    public MovimentacaoResponseDTO create(MovimentacaoRequestDTO dto) {

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        // Criar entity base
        Movimentacao mov = movimentacaoMapper.toEntity(dto);

        // Setar produto (foi ignorado pelo mapper)
        mov.setProduto(produto);

        // Estoque anterior
        Integer estoqueAtual = produto.getEstoqueAtual();
        mov.setEstoqueAnterior(estoqueAtual);

        // Aplicar regra da operação
        Integer novoEstoque = aplicarRegraDeEstoque(dto.tipoMovimentacao(), estoqueAtual, dto.quantidade());

        mov.setEstoquePosterior(novoEstoque);

        // Atualizar estoque do produto
        produto.setEstoqueAtual(novoEstoque);
        produtoRepository.save(produto);

        // Salvar movimentação
        Movimentacao saved = movimentacaoRepository.save(mov);

        return movimentacaoMapper.toResponseDTO(saved);
    }

    // -------------------------------------------------------
    // Buscar por ID
    // -------------------------------------------------------
    public MovimentacaoResponseDTO findById(UUID id) {
        Movimentacao mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentação não encontrada"));

        return movimentacaoMapper.toResponseDTO(mov);
    }

    // -------------------------------------------------------
    // Regras de estoque
    // -------------------------------------------------------
    private Integer aplicarRegraDeEstoque(TipoMovimentacao tipo, Integer estoqueAtual, Integer quantidade) {

        if (quantidade == null || quantidade <= 0)
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");

        return switch (tipo) {

            case ENTRADA -> estoqueAtual + quantidade;

            case SAIDA -> {
                if (quantidade > estoqueAtual)
                    throw new IllegalArgumentException("Estoque insuficiente para saída");
                yield estoqueAtual - quantidade;
            }
        };
    }

    // -------------------------------------------------------
    // Listar todas (pode ser com paginação futuramente)
    // -------------------------------------------------------
    public java.util.List<com.br.stockpro.dtos.movimentacao.MovimentacaoListDTO> listAll() {
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findAll());
    }
}
