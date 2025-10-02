package com.moreno.ecommerceMoreno.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor; // É bom ter um construtor sem argumentos
import lombok.AllArgsConstructor; // E um com todos os argumentos
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Integer quantidade;

        @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
        private BigDecimal precoUnitario;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "pedido_id", nullable = false)
        private Pedido pedido;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "produto_id", nullable = false)
        private Produto produto;

        @JdbcTypeCode(SqlTypes.JSON)
        @Column(columnDefinition = "jsonb")
        private Map<String, String> personalizacao;

        @Transient
        public BigDecimal getValorTotal() {
                if (precoUnitario == null || quantidade == null) {
                        return BigDecimal.ZERO;
                }
                return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
}