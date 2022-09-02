package com.example.konko.account.repository;

import com.example.konko.User.User;
import com.example.konko.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

    List<Account> findAllAccountByUserId(User id);

    Optional<Account> findAccountById(Long Id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Account a SET a.balance = ?2 WHERE a.id = ?1"
    )
    void updateAccountBalance(Long accountId, Double newAccountBalance);

//    Account updateAccount(Account account);
//
//    @Transactional
//    @Modifying
//    @Query(("UPDATE Account a " +
//            "SET a. = TRUE WHERE a.email = ?1"))


}
