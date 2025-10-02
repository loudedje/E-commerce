package com.moreno.ecommerceMoreno.service;

import com.moreno.ecommerceMoreno.Enum.StatusPedido;
import com.moreno.ecommerceMoreno.dto.ItemCarrinhoDTO;
import com.moreno.ecommerceMoreno.dto.PedidoCriacaoDTO;
import com.moreno.ecommerceMoreno.dto.PedidoDTO;
import com.moreno.ecommerceMoreno.exception.BusinessRuleException;
import com.moreno.ecommerceMoreno.exception.ResourceNotFoundException;
import com.moreno.ecommerceMoreno.mapper.PedidoMapper;
import com.moreno.ecommerceMoreno.model.*;
import com.moreno.ecommerceMoreno.model.json.AtributosProduto;
import com.moreno.ecommerceMoreno.model.json.OpcaoPaga;
import com.moreno.ecommerceMoreno.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UserRepository userRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository, UserRepository userRepository,
                         ProdutoRepository produtoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.userRepository = userRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoMapper = pedidoMapper;
    }
    @Transactional
    public PedidoDTO criarPedido(PedidoCriacaoDTO dto) {
        User cliente = userRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + dto.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemCarrinhoDTO itemCarrinho : dto.getItens()) {
            Produto produto = produtoRepository.findById(itemCarrinho.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemCarrinho.getProdutoId()));

            if (produto.getEstoque() < itemCarrinho.getQuantidade()) {
                throw new BusinessRuleException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            BigDecimal precoFinalItem = produto.getPreco();
            Map<String, String> personalizacoesCliente = itemCarrinho.getPersonalizacao();
            AtributosProduto regrasDePreco = produto.getAtributosExtra();
            if (regrasDePreco != null && regrasDePreco.getOpcoesPagas() != null && personalizacoesCliente != null) {
                for (OpcaoPaga opcao : regrasDePreco.getOpcoesPagas()) {
                    if (personalizacoesCliente.containsKey(opcao.getChave())) {
                        precoFinalItem = precoFinalItem.add(opcao.getPrecoAdicional());
                    }
                }
            }

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemCarrinho.getQuantidade());
            itemPedido.setPrecoUnitario(precoFinalItem);
            itemPedido.setPersonalizacao(personalizacoesCliente);
            pedido.adicionarItem(itemPedido);

            produto.setEstoque(produto.getEstoque() - itemCarrinho.getQuantidade());
            produtoRepository.save(produto);

            valorTotal = valorTotal.add(itemPedido.getValorTotal());
        }

        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
        return pedidoMapper.toDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));

        pedido.setStatus(novoStatus);

        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoAtualizado);
    }
}