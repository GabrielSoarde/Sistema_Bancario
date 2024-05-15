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
    public Users(String name, String lastName, Long cpf) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        setAccountNumber();
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
    public Users(){
    }
    public Integer getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber() {
        Random random = new Random();
        int min = 105;
        int max = 999;
        this.accountNumber = random.nextInt(max - min + 1) + min;
    }
    public void setAccount(Accounts account){
        this.account = account;
    }

}
