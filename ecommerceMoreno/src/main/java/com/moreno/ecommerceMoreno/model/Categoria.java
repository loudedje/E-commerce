package com.moreno.ecommerceMoreno.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String nome;
}