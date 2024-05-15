package com.example.demo.services;

import com.example.demo.entities.Accounts;
import com.example.demo.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    @Autowired
    private AccountsRepository accountsRepository;

    public BigDecimal realizarTransferencia(Long senderAccountId, Long receiverAccountId, BigDecimal amount) {
        // Cria uma instância de Accounts para o remetente
        Accounts senderAccount = accountsRepository.findById(senderAccountId).orElse(null);
        if (senderAccount == null) {
            // Trate o caso em que a conta do remetente não é encontrada
        }

        // Cria uma instância de Accounts para o destinatário
        Accounts receiverAccount = accountsRepository.findById(receiverAccountId).orElse(null);
        if (receiverAccount == null) {
            // Trate o caso em que a conta do destinatário não é encontrada
        }

        // Realiza a transferência de um determinado valor da conta do remetente para a conta do destinatário
        BigDecimal saldoAtualRemetente = senderAccount.transfer(amount, receiverAccount);
        return saldoAtualRemetente;
    }
}
