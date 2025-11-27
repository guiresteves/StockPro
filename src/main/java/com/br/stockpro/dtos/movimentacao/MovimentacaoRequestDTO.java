package com.br.stockpro.dtos.movimentacao;

import com.br.stockpro.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MovimentacaoRequestDTO(
        @NotNull
        UUID produtoId,
        @NotNull
        TipoMovimentacao tipoMovimentacao,
        @NotNull
        Integer quantidade,
        String observacao,
        UUID lojaOrigemId,
        UUID lojaDestinoId,
        UUID usuarioId,
        UUID referenciaId,
        String referenciaTipo
) {}
