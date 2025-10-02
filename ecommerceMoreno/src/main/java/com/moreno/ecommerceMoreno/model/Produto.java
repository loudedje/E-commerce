package com.moreno.ecommerceMoreno.model;

import com.moreno.ecommerceMoreno.model.json.AtributosProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    private String urlImagem;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> galeriaImagens;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private int estoque;

    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private boolean ativo = true;

    private Boolean personalizavel;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "atributos_extra", columnDefinition = "jsonb")
    private AtributosProduto atributosExtra;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}