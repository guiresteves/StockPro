package com.br.stockpro.dtos.movimentacao;

import java.util.UUID;

public record AjusteRequestDTO(
        UUID produtoId,
        Integer novoEstoque,
        String observacao,
        UUID usuarioId
) {
}
