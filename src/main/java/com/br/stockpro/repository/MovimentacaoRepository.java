package com.br.stockpro.repository;

import com.br.stockpro.enums.TipoMovimentacao;
import com.br.stockpro.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.*;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    List<Movimentacao> findByProdutoId(UUID produtoId);

    List<Movimentacao> findByTipoMovimentacao(TipoMovimentacao tipoMovimentacao);

    List<Movimentacao> findAllByOrderByCreatedAtDesc();

    List<Movimentacao> findByReferenciaId(UUID referenciaId);

    List<Movimentacao> findByProdutoIdOrderByCreatedAtDesc(UUID produtoId);

    List<Movimentacao> findByCreatedAtBetween(Instant inicio, Instant fim);

    List<Movimentacao> findByProdutoIdAndCreatedAtBetween(
            UUID produtoId,
            Instant inicio,
            Instant fim
    );

    List<Movimentacao> findByLojaId(UUID lojaId);

    List<Movimentacao> findByLojaIdAndCreatedAtBetween(
            UUID lojaId,
            Instant inicio,
            Instant fim
    );

    List<Movimentacao> findByTipoMovimentacaoAndCreatedAtBetween(
            TipoMovimentacao tipo,
            Instant inicio,
            Instant fim
    );

    List<Movimentacao> findByProdutoIdAndTipoMovimentacaoAndCreatedAtBetween(
            UUID produtoId,
            TipoMovimentacao tipo,
            Instant inicio,
            Instant fim
    );

    List<Movimentacao> findByLojaIdAndTipoMovimentacaoAndCreatedAtBetween(
            UUID lojaId,
            TipoMovimentacao tipo,
            Instant inicio,
            Instant fim
    );

    List<Movimentacao> findByProdutoIdAndLojaIdAndCreatedAtBetween(
            UUID produtoId,
            UUID lojaId,
            Instant inicio,
            Instant fim
    );

}
