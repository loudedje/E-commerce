package com.moreno.ecommerceMoreno.mapper;

import com.moreno.ecommerceMoreno.dto.ProdutoDTO;
import com.moreno.ecommerceMoreno.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProdutoDTO toDTO(Produto produto) {
        if (produto == null) {
            return null;
        }
        ProdutoDTO dto = new ProdutoDTO();

        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setUrlImagem(produto.getUrlImagem());
        dto.setAtributosExtra(produto.getAtributosExtra());

        if (produto.getCategoria() != null) {
            dto.setCategoriaId(produto.getCategoria().getId());
            dto.setCategoriaNome(produto.getCategoria().getNome());
        }

        return dto;
    }
}