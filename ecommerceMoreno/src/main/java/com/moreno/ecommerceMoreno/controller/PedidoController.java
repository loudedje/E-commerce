package com.moreno.ecommerceMoreno.controller;

import com.moreno.ecommerceMoreno.Enum.StatusPedido;
import com.moreno.ecommerceMoreno.dto.PedidoCriacaoDTO;
import com.moreno.ecommerceMoreno.dto.PedidoDTO;
import com.moreno.ecommerceMoreno.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoCriacaoDTO pedidoCriacaoDTO) {
        PedidoDTO novoPedido = pedidoService.criarPedido(pedidoCriacaoDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoPedido.getId())
                .toUri();

        return ResponseEntity.created(location).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam("status") StatusPedido novoStatus) {

        PedidoDTO pedidoAtualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedidoAtualizado);
    }

}