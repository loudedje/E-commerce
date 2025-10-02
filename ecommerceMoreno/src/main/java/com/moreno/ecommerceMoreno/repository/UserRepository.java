package com.moreno.ecommerceMoreno.repository;

import com.moreno.ecommerceMoreno.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

}
