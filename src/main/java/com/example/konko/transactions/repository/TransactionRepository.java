package com.example.konko.transactions.repository;

import com.example.konko.User.User;
import com.example.konko.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findAllByUserId(User id);
    Optional<Transaction> findById(Long id);
}
