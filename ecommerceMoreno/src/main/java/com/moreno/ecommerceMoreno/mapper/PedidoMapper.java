package com.moreno.ecommerceMoreno.mapper;

import com.moreno.ecommerceMoreno.dto.ItemPedidoDTO;
import com.moreno.ecommerceMoreno.dto.PedidoDTO;
import com.moreno.ecommerceMoreno.model.ItemPedido;
import com.moreno.ecommerceMoreno.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setClienteNome(pedido.getCliente().getNome());
        dto.setStatus(pedido.getStatus());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setValorTotal(pedido.getValorTotal());

        dto.setItens(pedido.getItens().stream().map(this::toItemDTO).collect(Collectors.toList()));

        return dto;
    }

    private ItemPedidoDTO toItemDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setProdutoId(item.getProduto().getId());
        dto.setNomeProduto(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setPersonalizacao(item.getPersonalizacao());

        return dto;
    }
}