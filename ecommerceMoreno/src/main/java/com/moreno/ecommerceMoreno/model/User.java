package com.moreno.ecommerceMoreno.model;

import com.moreno.ecommerceMoreno.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "O nome não pode estar em branco")
        @Size(min = 2, max = 100)
        @Column(nullable = false, length = 100)
        private String nome;

        @Email(message = "Formato de email inválido")
        @NotBlank
        @Column(unique = true, nullable = false)
        private String email;

        @NotBlank
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") // Validação do tamanho da senha original
        @Column(name = "senha_hash", nullable = false) // A senha será armazenada como hash
        private String senha;

        @Column(length = 20)
        private String telefone;

        @Column(length = 255)
        private String endereco;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        private Role role = Role.CLIENTE;
}