package com.moreno.ecommerceMoreno.repository;

import com.moreno.ecommerceMoreno.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
