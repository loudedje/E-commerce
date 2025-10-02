package com.moreno.ecommerceMoreno.repository;

import com.moreno.ecommerceMoreno.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
