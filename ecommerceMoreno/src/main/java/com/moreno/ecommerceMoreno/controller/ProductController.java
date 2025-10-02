package com.moreno.ecommerceMoreno.controller;

import com.moreno.ecommerceMoreno.dto.ProdutoDTO;
import  com.moreno.ecommerceMoreno.dto.EstoqueUpdateDTO;
import com.moreno.ecommerceMoreno.service.ProductService;
import jakarta.validation.Valid; // Importe para validação
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products") // Um bom padrão de versionamento de API
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduct(@Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO savedProduct = productService.createProduct(produtoDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO updatedProduct = productService.updateProduct(id, produtoDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProducts() {
        List<ProdutoDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProductById(@PathVariable Long id) {
        ProdutoDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoDTO> adicionarEstoque(@PathVariable Long id, @RequestBody EstoqueUpdateDTO estoqueUpdateDTO) {

        int quantidade = estoqueUpdateDTO.getQuantidadeAdicionar();

        ProdutoDTO produtoAtualizado = productService.adicionarEstoque(id, quantidade);

        return ResponseEntity.ok(produtoAtualizado);
    }
}