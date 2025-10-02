package com.moreno.ecommerceMoreno.dto;

import com.moreno.ecommerceMoreno.model.json.AtributosProduto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String urlImagem;
    private Long categoriaId;
    private String categoriaNome;
    private AtributosProduto atributosExtra;

    private int estoque;
    private boolean ativo;
    private Boolean personalizavel;
}