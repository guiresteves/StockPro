package com.br.stockpro.controller;

import com.br.stockpro.dtos.categoria.CategoriaListDTO;
import com.br.stockpro.dtos.categoria.CategoriaRequestDTO;
import com.br.stockpro.dtos.categoria.CategoriaResponseDTO;
import com.br.stockpro.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Gerenciamento das categorias de produtos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(
            summary = "Criar uma nova categoria",
            description = "Cria uma categoria garantindo que o nome não esteja duplicado."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso",
            content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))
    )
    @ApiResponse(
            responseCode = "409",
            description = "Já existe outra categoria com o mesmo nome"
    )
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO categoria) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas.")
    @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso"
    )
    public ResponseEntity<List<CategoriaListDTO>> findAll() {
        List<CategoriaListDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Busca uma categoria pelo seu ID.")
    @ApiResponse(
            responseCode = "200",
            description = "Categoria encontrada"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada"
    )
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable UUID id) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza o nome da categoria informada.")
    @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Já existe outra categoria com o mesmo nome"
    )
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody CategoriaRequestDTO categoria) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.update(id, categoria);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria", description = "Remove uma categoria pelo ID.")
    @ApiResponse(
            responseCode = "204",
            description = "Categoria removida com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada"
    )
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
