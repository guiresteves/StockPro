package com.br.stockpro.dtos.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "DTO de resposta detalhada de categoria")
public record CategoriaResponseDTO(
        UUID id,
        String nome,
        Instant createdAt,
        Instant updatedAt
) {
}
