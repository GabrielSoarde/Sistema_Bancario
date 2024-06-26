package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByAccountNumberAndCpf(Long accountNumber, Long cpf);
    Users findByAccountNumber(Long accountNumber);
}
