// RegisterRequestDTO.java
package com.moreno.ecommerceMoreno.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String nome;
    private String email;
    private String password;
}