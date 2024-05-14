package com.example.demo;

import com.example.demo.entities.Users;
import com.example.demo.entities.Accounts;
import com.example.demo.repositories.AccountsRepository;
import com.example.demo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private AccountsRepository accountsRepository;

	@Override
	public void run(String... args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("POR FAVOR DIGITE A OPÇÃO DESEJADA");
		System.out.println("1 - CRIAR CONTA\n2 - ACESSAR CONTA\n3 - SAIR");
		int option = sc.nextInt();

		if (option == 1) {
			Users u = new Users();
			Accounts a = new Accounts();

			System.out.print("DIGITE O SEU NOME: ");
			String name = sc.next();
			u.setName(name);

			System.out.print("DIGITE SEU SOBRENOME: ");
			String lastName = sc.next();
			u.setLastName(lastName);

			System.out.print("DIGITE SEU CPF: ");
			Long cpf = sc.nextLong();
			u.setCpf(cpf);

			u.setFullName(name, lastName);

			usersRepository.save(u);
			accountsRepository.save(a);

			System.out.println(u.getFullName() + "\nSEU SALDO É DE: " + u.getBalance() + "\nNÚMERO DA SUA CONTA: " + u.getAccountNumber());
		}
	}
}
