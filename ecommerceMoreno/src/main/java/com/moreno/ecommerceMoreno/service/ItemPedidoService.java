package com.moreno.ecommerceMoreno.service;

import com.moreno.ecommerceMoreno.model.ItemPedido;
import com.moreno.ecommerceMoreno.model.Pedido;

import com.moreno.ecommerceMoreno.repository.ItemPedidoRepository;
import com.moreno.ecommerceMoreno.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public ItemPedido adicionarItem(Long pedidoId, ItemPedido itemPedido) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);

        if (pedidoOptional.isEmpty()) {
            throw new RuntimeException("Pedido não encontrado");
        }

        Pedido pedido = pedidoOptional.get();
        itemPedido.setPedido(pedido);
        ItemPedido salvo = itemPedidoRepository.save(itemPedido);

        pedido.getItens().add(salvo);
        pedido.calcularValorTotal();
        pedidoRepository.save(pedido);

        return salvo;
    }

    public void removerItem(Long itemId) {
        Optional<ItemPedido> itemOptional = itemPedidoRepository.findById(itemId);

        if (itemOptional.isPresent()) {
            Pedido pedido = itemOptional.get().getPedido();
            itemPedidoRepository.delete(itemOptional.get());

            pedido.calcularValorTotal();
            pedidoRepository.save(pedido);
        }
    }
}
