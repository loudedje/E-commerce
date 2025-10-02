package com.moreno.ecommerceMoreno.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoCriacaoDTO {
    private Long clienteId;

    private List<ItemCarrinhoDTO> itens;
}