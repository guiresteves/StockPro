package com.br.stockpro.dtos.categoria;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO usado para listagem de categorias")
public record CategoriaListDTO(
        UUID id,
        String nome
) {
}
