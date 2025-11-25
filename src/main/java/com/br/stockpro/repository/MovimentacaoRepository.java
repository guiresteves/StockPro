package com.br.stockpro.repository;

import com.br.stockpro.enums.TipoMovimentacao;
import com.br.stockpro.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.*;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {

    // Buscar movimentações por produto
    List<Movimentacao> findByProdutoId(UUID produtoId);

    // Buscar por tipo (ENTRADA / SAIDA)
    List<Movimentacao> findByTipoMovimentacao(TipoMovimentacao tipoMovimentacao);

    // Buscar movimentações mais recentes primeiro
    List<Movimentacao> findAllByOrderByCreatedAtDesc();

    // Buscar por referencia externa (auditoria)
    List<Movimentacao> findByReferenciaId(UUID referenciaId);

    // Buscar movimentações de um produto ordenadas (histórico)
    List<Movimentacao> findByProdutoIdOrderByCreatedAtDesc(UUID produtoId);

    // Buscar movimentações dentro de um intervalo de datas
    List<Movimentacao> findByCreatedAtBetween(Instant inicio, Instant fim);

    // Buscar movimentações de um produto por intervalo de datas
    List<Movimentacao> findByProdutoIdAndCreatedAtBetween(
            UUID produtoId,
            Instant inicio,
            Instant fim
    );

    // Filtrar por tipo e intervalo (relatórios)
    List<Movimentacao> findByTipoMovimentacaoAndCreatedAtBetween(
            TipoMovimentacao tipo,
            Instant inicio,
            Instant fim
    );
}
