package com.moreno.ecommerceMoreno.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class ItemPedidoDTO {
    private Long produtoId;
    private String nomeProduto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private Map<String, String> personalizacao;

}