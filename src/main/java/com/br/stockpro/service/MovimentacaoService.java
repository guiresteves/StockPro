package com.br.stockpro.service;

import com.br.stockpro.dtos.movimentacao.MovimentacaoListDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    @Transactional
    public MovimentacaoResponseDTO create(MovimentacaoRequestDTO dto) {

        if (dto.tipoMovimentacao() == TipoMovimentacao.AJUSTE) {
            throw new IllegalArgumentException(
                    "Movimentações do tipo AJUSTE devem ser registradas pelo endpoint específico de ajuste."
            );
        }

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        Movimentacao movimentacao = movimentacaoMapper.toEntity(dto);
        movimentacao.setProduto(produto);
        movimentacao.setReferenciaId(dto.referenciaId());
        movimentacao.setReferenciaTipo(dto.referenciaTipo());
        movimentacao.setUsuarioId(dto.usuarioId());

        Integer estoqueAtual = produto.getEstoqueAtual();
        movimentacao.setEstoqueAnterior(estoqueAtual);

        Integer novoEstoque = calcularNovoEstoque(
                dto.tipoMovimentacao(),
                estoqueAtual,
                dto.quantidade()
        );

        movimentacao.setEstoquePosterior(novoEstoque);

        produto.setEstoqueAtual(novoEstoque);
        produtoRepository.save(produto);

        Movimentacao saved = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toResponseDTO(saved);
    }

    private Integer calcularNovoEstoque(TipoMovimentacao tipo, Integer estoqueAtual, Integer quantidade) {

        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        return switch (tipo) {
            case ENTRADA -> estoqueAtual + quantidade;

            case SAIDA -> {
                if (quantidade > estoqueAtual) {
                    throw new IllegalArgumentException("Estoque insuficiente para saída");
                }
                yield estoqueAtual - quantidade;
            }

            case AVARIA -> {
                if (quantidade > estoqueAtual) {
                    throw new IllegalArgumentException("Quantidade de avaria maior que o estoque atual");
                }
                yield estoqueAtual - quantidade;
            }

            default -> throw new IllegalArgumentException(
                    "Tipo de movimentação não suportado neste endpoint"
            );
        };
    }

    public MovimentacaoResponseDTO findById(UUID id) {
        Movimentacao mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimentação não encontrada"));

        return movimentacaoMapper.toResponseDTO(mov);
    }

    public List<MovimentacaoListDTO> findAll() {
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findAll());
    }

    public List<MovimentacaoListDTO> findByProduto(UUID produtoId) {
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findByProdutoId(produtoId));
    }

    public List<MovimentacaoListDTO> findByTipoMovimentacao(TipoMovimentacao tipo) {
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findByTipoMovimentacao(tipo));
    }

    public List<MovimentacaoListDTO> findByReferencia(UUID referenciaId) {
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findByReferenciaId(referenciaId));
    }

    public List<MovimentacaoListDTO> findByPeriodo(Instant inicio, Instant fim) {
        validarPeriodo(inicio, fim);
        return movimentacaoMapper.toListDTO(movimentacaoRepository.findByCreatedAtBetween(inicio, fim));
    }

    public List<MovimentacaoListDTO> findByProdutoPeriodo(UUID produtoId, Instant inicio, Instant fim) {
        validarPeriodo(inicio, fim);

        return movimentacaoMapper.toListDTO(
                movimentacaoRepository.findByProdutoIdAndCreatedAtBetween(produtoId, inicio, fim)
        );
    }

    private void validarPeriodo(Instant inicio, Instant fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("As datas início e fim são obrigatórias.");
        }
        if (fim.isBefore(inicio)) {
            throw new IllegalArgumentException("A data final não pode ser menor que a data inicial.");
        }
    }
}
