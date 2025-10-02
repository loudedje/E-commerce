package com.moreno.ecommerceMoreno.service;

import com.moreno.ecommerceMoreno.dto.CategoriaDTO;
import com.moreno.ecommerceMoreno.exception.ResourceNotFoundException;
import com.moreno.ecommerceMoreno.model.Categoria;
import com.moreno.ecommerceMoreno.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO criar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        dto.setId(categoriaSalva.getId());
        return dto;
    }

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream().map(cat -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(cat.getId());
            dto.setNome(cat.getNome());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}