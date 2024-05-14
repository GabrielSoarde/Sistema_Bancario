package com.example.demo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private BigDecimal balance = BigDecimal.ZERO;
    public BigDecimal deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("O VALOR DE DEPÓSITO DEVE SER ACIMA DE ZERO (0)");
            return balance;
        }
        balance = balance.add(amount);
        return balance;
    }
    public BigDecimal withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            System.out.println("SALDO ABAIXO DO VALOR PARA SAQUE!");
            return balance;
        }
        balance = balance.subtract(amount);
        return balance;
    }
}
