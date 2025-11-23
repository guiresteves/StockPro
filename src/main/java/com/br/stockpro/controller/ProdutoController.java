package com.br.stockpro.controller;


import com.br.stockpro.dtos.produto.ProdutoListDTO;
import com.br.stockpro.dtos.produto.ProdutoRequestDTO;
import com.br.stockpro.dtos.produto.ProdutoResponseDTO;
import com.br.stockpro.service.ProdutoService;
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
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> create(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoListDTO>> findAll() {
        List<ProdutoListDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable UUID id) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.findById(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoResponseDTO = produtoService.update(id, dto);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtros/estoque-baixo")
    public ResponseEntity<List<ProdutoListDTO>> listarComEstoqueBaixo() {
        return ResponseEntity.ok(produtoService.listarComEstoqueBaixo());
    }

    @GetMapping("/filtros/ativos")
    public ResponseEntity<List<ProdutoListDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }

    @GetMapping("/filtros/com-validade")
    public ResponseEntity<List<ProdutoListDTO>> listarComValidade() {
        return ResponseEntity.ok(produtoService.listarComValidade());
    }

    @GetMapping("/filtros/vencidos")
    public ResponseEntity<List<ProdutoListDTO>> listarVencidos() {
        return ResponseEntity.ok(produtoService.listarVencidos());
    }

    @GetMapping("/filtros/vencendo-antes")
    public ResponseEntity<List<ProdutoListDTO>> listarVencendoAntes(
            @RequestParam LocalDate data) {

        return ResponseEntity.ok(produtoService.listarVencendoAntes(data));
    }
}
