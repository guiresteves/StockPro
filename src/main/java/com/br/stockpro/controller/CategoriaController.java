package com.br.stockpro.controller;

import com.br.stockpro.dtos.categoria.CategoriaListDTO;
import com.br.stockpro.dtos.categoria.CategoriaRequestDTO;
import com.br.stockpro.dtos.categoria.CategoriaResponseDTO;
import com.br.stockpro.service.CategoriaService;
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
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO categoria) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaListDTO>> findAll() {
        List<CategoriaListDTO> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable UUID id) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody CategoriaRequestDTO categoria) {
        CategoriaResponseDTO categoriaResponseDTO = categoriaService.update(id, categoria);
        return ResponseEntity.ok(categoriaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
