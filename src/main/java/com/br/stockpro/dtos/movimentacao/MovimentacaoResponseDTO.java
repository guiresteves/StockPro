package com.br.stockpro.dtos.movimentacao;

import com.br.stockpro.enums.TipoMovimentacao;

import java.time.Instant;
import java.util.UUID;

public record MovimentacaoResponseDTO(
        UUID id,
        UUID produtoId,
        String produtoNome,
        TipoMovimentacao tipoMovimentacao,
        Integer quantidade,
        Integer estoqueAnterior,
        Integer estoquePosterior,
        Integer diferenca,
        String observacao,
        UUID lojaOrigemId,
        UUID lojaDestinoId,
        UUID usuarioId,
        UUID referenciaId,
        String referenciaTipo,
        Instant createdAt,
        Instant updatedAt
) {
}
