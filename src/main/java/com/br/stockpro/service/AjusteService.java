package com.br.stockpro.service;

import com.br.stockpro.dtos.movimentacao.AjusteRequestDTO;
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

@Service
@RequiredArgsConstructor
public class AjusteService {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    @Transactional
    public MovimentacaoResponseDTO ajustarEstoque(AjusteRequestDTO dto) {

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

        Movimentacao mov = new Movimentacao();
        mov.setProduto(produto);
        mov.setTipoMovimentacao(TipoMovimentacao.AJUSTE);

        Integer estoqueAnterior = produto.getEstoqueAtual();
        Integer estoqueNovo = dto.novoEstoque();

        mov.setEstoqueAnterior(estoqueAnterior);
        mov.setEstoquePosterior(estoqueNovo);
        mov.setDiferencaAjuste(estoqueNovo - estoqueAnterior);
        mov.setObservacao(dto.observacao());
        mov.setUsuarioId(dto.usuarioId());

        produto.setEstoqueAtual(estoqueNovo);
        produtoRepository.save(produto);

        Movimentacao saved = movimentacaoRepository.save(mov);
        return movimentacaoMapper.toResponseDTO(saved);
    }
}
