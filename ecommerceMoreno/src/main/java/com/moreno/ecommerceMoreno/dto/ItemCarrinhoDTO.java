package com.moreno.ecommerceMoreno.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ItemCarrinhoDTO {
    private Long produtoId;
    private int quantidade;
    private Map<String, String> personalizacao;

}