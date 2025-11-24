package com.br.stockpro.controller;


import com.br.stockpro.dtos.produto.ProdutoListDTO;
import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.service.ProdutoService;
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

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Operações de gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @Operation(
            summary = "Criar produto",
            description = "Cria um novo produto validando categoria, validade e código de barras."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))
    )
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    @ApiResponse(responseCode = "409", description = "Código de barras duplicado")
    public ResponseEntity<ProdutoResponseDTO> create(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponseDTO);
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados.")
    public ResponseEntity<List<ProdutoListDTO>> findAll() {
        List<ProdutoListDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable UUID id) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.findById(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados do produto, incluindo estoque, validade e categoria."
    )
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "409", description = "Código de barras duplicado")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.update(id, dto);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto")
    @ApiResponse(responseCode = "204")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtros/estoque/baixo")
    @Operation(summary = "Listar produtos abaixo do estoque mínimo")
    public ResponseEntity<List<ProdutoListDTO>> listarComEstoqueBaixo() {
        return ResponseEntity.ok(produtoService.listarComEstoqueBaixo());
    }

    @GetMapping("/filtros/ativos")
    @Operation(summary = "Listar produtos ativos")
    public ResponseEntity<List<ProdutoListDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }

    @GetMapping("/filtros/validade")
    @Operation(summary = "Listar produtos que possuem validade")
    public ResponseEntity<List<ProdutoListDTO>> listarComValidade() {
        return ResponseEntity.ok(produtoService.listarComValidade());
    }

    @GetMapping("/filtros/vencidos")
    @Operation(summary = "Listar produtos vencidos")
    public ResponseEntity<List<ProdutoListDTO>> listarVencidos() {
        return ResponseEntity.ok(produtoService.listarVencidos());
    }

    @GetMapping("/filtros/vencendo")
    @Operation(summary = "Listar produtos com validade antes da data informada")
    public ResponseEntity<List<ProdutoListDTO>> listarVencendoAntes(
            @RequestParam LocalDate data) {

        return ResponseEntity.ok(produtoService.listarVencendoAntes(data));
    }
}
