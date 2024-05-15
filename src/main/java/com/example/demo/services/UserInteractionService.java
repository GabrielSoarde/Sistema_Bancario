package com.example.demo.services;

import com.example.demo.entities.Users;
import com.example.demo.entities.Accounts;
import com.example.demo.repositories.AccountsRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Scanner;

@Service
public class UserInteractionService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    public void handleUserInteraction() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("POR FAVOR DIGITE A OPÇÃO DESEJADA");
            System.out.println("1 - CRIAR CONTA\n2 - ACESSAR CONTA\n3 - SAIR");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    createAccount(sc);
                    break;
                case 2:
                    accessAccount(sc);
                    break;
                case 3:
                    System.out.println("OBRIGADO POR USAR NOSSO SISTEMA. ATÉ LOGO!");
                    running = false;
                    System.exit(0);
                default:
                    System.out.println("OPÇÃO INVÁLIDA. POR FAVOR, ESCOLHA UMA OPÇÃO VÁLIDA.");
            }
        }
        sc.close();
    }

    private void createAccount(Scanner sc) {
        System.out.print("DIGITE O SEU NOME: ");
        String name = sc.next();
        System.out.print("DIGITE SEU SOBRENOME: ");
        String lastName = sc.next();
        System.out.print("DIGITE SEU CPF: ");
        Long cpf = sc.nextLong();

        Users user = new Users(name, lastName, cpf);
        user.setFullName(name, lastName);
        user.setAccountNumber();

        Accounts account = new Accounts();
        account.setUser(user);

        usersRepository.save(user);
        accountsRepository.save(account);

        System.out.println(user.getFullName() + "\nSEU SALDO É DE: " + user.getBalance() + "\nNÚMERO DA SUA CONTA: " + user.getAccountNumber());
    }

    private void accessAccount(Scanner sc) {
        System.out.print("POR FAVOR DIGITE O NUMERO DA CONTA: ");
        Long accountNumber = sc.nextLong();
        System.out.print("POR FAVOR DIGITE O CPF: ");
        Long cpf = sc.nextLong();

        if (realizarLogin(accountNumber, cpf)) {
            System.out.println("LOGIN REALIZADO COM SUCESSO");
            Users user = usersRepository.findByAccountNumberAndCpf(accountNumber, cpf);
            System.out.println("SALDO DA CONTA: " + user.getBalance());

            handleAccountOperations(user, sc);
        } else {
            System.out.println("NÚMERO DA CONTA OU CPF INVÁLIDO.");
        }
    }

    private void handleAccountOperations(Users user, Scanner sc) {
        while (true) {
            System.out.println("POR FAVOR DIGITE A OPÇÃO DESEJADA");
            System.out.println("1 - SAQUE\n2 - DEPÓSITO\n3 - TRANSFERIR\n4 - VOLTAR PARA O MENU PRINCIPAL\n5 - SAIR");
            int operation = sc.nextInt();

            switch (operation) {
                case 1:
                    withdraw(user, sc);
                    break;
                case 2:
                    deposit(user, sc);
                    break;
                case 3:
                    System.out.print("DIGITE O VALOR DA TRANSFERÊNCIA: ");
                    BigDecimal transferAmount = sc.nextBigDecimal();
                    transfer(user, sc, transferAmount);
                case 4:
                    return;
                case 5:
                    System.out.println("OBRIGADO POR USAR NOSSO SISTEMA. ATÉ LOGO!");
                    System.exit(0);
                default:
                    System.out.println("OPÇÃO INVÁLIDA. POR FAVOR, ESCOLHA UMA OPÇÃO VÁLIDA.");
            }
        }
    }

    private void withdraw(Users user, Scanner sc) {
        System.out.print("INDIQUE O VALOR DO SAQUE: ");
        BigDecimal withdrawAmount = sc.nextBigDecimal();

        if (user.getBalance().compareTo(withdrawAmount) >= 0) {
            BigDecimal newBalance = user.getBalance().subtract(withdrawAmount);
            user.setBalance(newBalance);
            usersRepository.save(user);
            System.out.println("SAQUE REALIZADO COM SUCESSO");
            System.out.println("NOVO SALDO: " + user.getBalance());
        } else {
            System.out.println("SALDO INSUFICIENTE PARA REALIZAR O SAQUE");
        }
    }

    private void deposit(Users user, Scanner sc) {
        System.out.print("INDIQUE O VALOR DO DEPÓSITO: ");
        BigDecimal depositAmount = sc.nextBigDecimal();

        BigDecimal newBalance = user.getBalance().add(depositAmount);
        user.setBalance(newBalance);
        usersRepository.save(user);
        System.out.println("DEPÓSITO REALIZADO COM SUCESSO");
        System.out.println("NOVO SALDO: " + user.getBalance());
    }
    public void transfer(Users sender, Scanner sc, BigDecimal amount) {
        System.out.print("POR FAVOR DIGITE O NÚMERO DA CONTA DO DESTINATÁRIO: ");
        Long receiverAccountNumber = sc.nextLong();
        System.out.print("POR FAVOR DIGITE O CPF DO DESTINATÁRIO: ");
        Long receiverCpf = sc.nextLong();

        Users receiver = usersRepository.findByAccountNumberAndCpf(receiverAccountNumber, receiverCpf);

        // Verifica se o destinatário foi encontrado
        if (receiver == null) {
            System.out.println("DESTINATÁRIO NÃO ENCONTRADO. CERTIFIQUE-SE DE QUE A CONTA E O  CPF ESTÃO CORRETOS.");
            return;
        }

        // Verifica se o remetente tem saldo suficiente para a transferência
        if (sender.getBalance().compareTo(amount) < 0) {
            System.out.println("SALDO INSUFICIENTE PARA REALIZAR A TRANSFERÊNCIA.");
            return;
        }

        // Executa a transferência
        BigDecimal newSenderBalance = sender.getBalance().subtract(amount);
        BigDecimal newReceiverBalance = receiver.getBalance().add(amount);

        sender.setBalance(newSenderBalance);
        receiver.setBalance(newReceiverBalance);

        usersRepository.save(sender);
        usersRepository.save(receiver);

        System.out.println("TRANSFERÊNCIA REALIZADA COM SUCESSO.");
        System.out.println("SEU SALDO: " + newSenderBalance);
    }

    private boolean realizarLogin(Long numeroConta, Long cpf) {
        Users user = usersRepository.findByAccountNumberAndCpf(numeroConta, cpf);
        return user != null;
    }
}
