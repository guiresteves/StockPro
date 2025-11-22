package com.br.stockpro.dtos.categoria;

import java.time.Instant;
import java.util.UUID;

public record CategoriaResponseDTO(
        UUID id,
        String nome,
        Instant createdAt,
        Instant updatedAt
) {
}
