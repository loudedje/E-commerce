package com.moreno.ecommerceMoreno.service;

import com.moreno.ecommerceMoreno.dto.ProdutoDTO;
import com.moreno.ecommerceMoreno.exception.BusinessRuleException;
import com.moreno.ecommerceMoreno.exception.ResourceNotFoundException;
import com.moreno.ecommerceMoreno.mapper.ProductMapper;
import com.moreno.ecommerceMoreno.model.Categoria;
import com.moreno.ecommerceMoreno.model.Produto;
import com.moreno.ecommerceMoreno.repository.CategoriaRepository;
import com.moreno.ecommerceMoreno.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, ProductMapper productMapper) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProdutoDTO createProduct(ProdutoDTO produtoDTO) {
        Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + produtoDTO.getCategoriaId()));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setUrlImagem(produtoDTO.getUrlImagem());
        produto.setCategoria(categoria);
        produto.setEstoque(produtoDTO.getEstoque());
        produto.setAtivo(produtoDTO.isAtivo());
        produto.setPersonalizavel(produtoDTO.getPersonalizavel());
        produto.setAtributosExtra(produtoDTO.getAtributosExtra());

        Produto produtoSalvo = produtoRepository.save(produto);
        return productMapper.toDTO(produtoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> getAllProducts() {
        return produtoRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO getProductById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return productMapper.toDTO(produto);
    }

    @Transactional
    public ProdutoDTO updateProduct(Long id, ProdutoDTO productDetails) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        produto.setNome(productDetails.getNome());
        produto.setDescricao(productDetails.getDescricao());
        produto.setPreco(productDetails.getPreco());
        produto.setUrlImagem(productDetails.getUrlImagem());
        produto.setEstoque(productDetails.getEstoque());
        produto.setAtivo(productDetails.isAtivo());
        produto.setPersonalizavel(productDetails.getPersonalizavel());
        produto.setAtributosExtra(productDetails.getAtributosExtra());

        if (productDetails.getCategoriaId() != null && (produto.getCategoria() == null || !productDetails.getCategoriaId().equals(produto.getCategoria().getId()))) {
            Categoria novaCategoria = categoriaRepository.findById(productDetails.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + productDetails.getCategoriaId()));
            produto.setCategoria(novaCategoria);
        }

        Produto produtoAtualizado = produtoRepository.save(produto);
        return productMapper.toDTO(produtoAtualizado);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoDTO adicionarEstoque(Long id, int quantidadeAdicionar) {
        if (quantidadeAdicionar <= 0) {
            throw new BusinessRuleException("A quantidade a ser adicionada deve ser maior que zero.");
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        int novoEstoque = produto.getEstoque() + quantidadeAdicionar;
        produto.setEstoque(novoEstoque);

        Produto produtoAtualizado = produtoRepository.save(produto);
        return productMapper.toDTO(produtoAtualizado);
    }
}