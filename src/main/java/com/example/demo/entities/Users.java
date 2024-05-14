package com.example.demo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Random;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String fullName;
    private Long cpf;
    private Integer accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Accounts account;
    public Users() {
        setAccountNumber();
    }
    public Users(String name, String lastName, Long cpf, Integer accountNumber, BigDecimal balance) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return "SEJA BEM VINDO " + name + " " + lastName;
    }
    public void setFullName(String name, String lastName) {
        this.fullName = name + " " + lastName;
    }
    public Long getCpf() {
        return cpf;
    }
    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
    public void setAccountNumber() {
        Random random = new Random();
        this.accountNumber = random.nextInt(900) + 100;
    }
    public Integer getAccountNumber(){
        return accountNumber;
    }

}
