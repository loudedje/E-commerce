package com.moreno.ecommerceMoreno.dto;

import com.moreno.ecommerceMoreno.Enum.StatusPedido;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNome;
    private List<ItemPedidoDTO> itens;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private LocalDateTime dataPedido;
}