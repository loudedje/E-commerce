package com.moreno.ecommerceMoreno.model;

import com.moreno.ecommerceMoreno.Enum.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime; // ou java.time.Instant para UTC
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private User cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "data_pedido", nullable = false)
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StatusPedido status;

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        item.setPedido(null);
    }

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusPedido.PENDENTE;
        }
    }

    public void calcularValorTotal() {
        this.valorTotal = this.itens.stream()
                .map(ItemPedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}