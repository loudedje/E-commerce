package com.moreno.ecommerceMoreno.controller;

import com.moreno.ecommerceMoreno.model.ItemPedido;
import com.moreno.ecommerceMoreno.service.ItemPedidoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/itens")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping("/pedido/{pedidoId}")
    public ItemPedido adicionarItem(@PathVariable Long pedidoId, @RequestBody ItemPedido itemPedido) {
        return itemPedidoService.adicionarItem(pedidoId, itemPedido);
    }

    @DeleteMapping("/{itemId}")
    public void removerItem(@PathVariable Long itemId) {
        itemPedidoService.removerItem(itemId);
    }
}
