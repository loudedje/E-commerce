package com.moreno.ecommerceMoreno.controller;

import com.moreno.ecommerceMoreno.dto.CategoriaDTO;
import com.moreno.ecommerceMoreno.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@RequestBody CategoriaDTO dto) {
        CategoriaDTO novaCategoria = categoriaService.criar(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaCategoria.getId())
                .toUri();

        return ResponseEntity.created(location).body(novaCategoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}