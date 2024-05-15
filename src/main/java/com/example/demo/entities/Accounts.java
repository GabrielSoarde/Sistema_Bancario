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
    public Accounts(){
    }
    public BigDecimal deposit(BigDecimal amount){
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
    public void setUser(Users user){
        this.user = user;
    }
    public BigDecimal transfer(BigDecimal amount, Accounts receiverAccount) {
        BigDecimal senderBalance = this.getBalance();
        if (senderBalance.compareTo(amount) >= 0) {
            // Retira o valor da conta do remetente
            BigDecimal newSenderBalance = senderBalance.subtract(amount);
            this.setBalance(newSenderBalance);

            // Deposita o valor na conta do destinatário
            BigDecimal receiverBalance = receiverAccount.getBalance();
            BigDecimal newReceiverBalance = receiverBalance.add(amount);
            receiverAccount.setBalance(newReceiverBalance);

            return newSenderBalance; // Retorna o novo saldo do remetente
        } else {
            System.out.println("Saldo insuficiente para realizar a transferência.");
            return senderBalance; // Retorna o saldo atual do remetente
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
