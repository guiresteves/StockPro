package com.br.stockpro.service;

import com.br.stockpro.dtos.categoria.CategoriaListDTO;
import com.br.stockpro.dtos.categoria.CategoriaRequestDTO;
import com.br.stockpro.dtos.categoria.CategoriaResponseDTO;
import com.br.stockpro.exceptions.BusinessException;
import com.br.stockpro.exceptions.NotFoundException;
import com.br.stockpro.mapper.CategoriaMapper;
import com.br.stockpro.model.Categoria;
import com.br.stockpro.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaResponseDTO create(CategoriaRequestDTO dto) {

        if (categoriaRepository.existsByNome(dto.nome())) {
            throw new BusinessException("Já existe uma categoria com esse nome.");
        }

        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    public List<CategoriaListDTO> findAll() {
        return categoriaMapper.toListDTO(categoriaRepository.findAll());
    }

    public CategoriaResponseDTO findById(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO update(UUID id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (categoriaRepository.existsByNomeAndIdNot(dto.nome(), id)) {
            throw new BusinessException("Já existe uma categoria com esse nome.");
        }

        categoriaMapper.updateEntityFromDTO(dto, categoria);
        return categoriaMapper.toResponseDTO(categoriaRepository.save(categoria));
    }

    public void delete(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrado"));

        categoriaRepository.delete(categoria);
    }
}
