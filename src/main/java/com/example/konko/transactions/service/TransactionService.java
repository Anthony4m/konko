package com.example.konko.transactions.service;

import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.account.Account;
import com.example.konko.account.repository.AccountRepository;
import com.example.konko.transactions.Transaction;
import com.example.konko.transactions.dto.TransactionDto;
import com.example.konko.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    public String createTransaction(TransactionDto transactionDto) {
        boolean userExist = userRepository.findUserById(transactionDto.getUserId()).isPresent();
        if (!userExist){
            return "User Not found";
        }
        User user = userRepository.findUserById(transactionDto.getUserId()).get();
        Account account = accountRepository.findAccountById(transactionDto.getAccountId()).get();
        return saveTransaction(
                new Transaction(
                        account,
                        transactionDto.getAmount(),
                        transactionDto.getDescription(),
                        transactionDto.getCatergory(),
                        transactionDto.getRecipient(),
                        LocalDateTime.now(),
                        user
                )
        );
    }

    private String saveTransaction(Transaction transaction) {

        transactionRepository.save(transaction);

        return transaction.toString();
    }

    public List<Transaction> getTransaction(Long id) {
        User user = userRepository.findUserById(id).get();
        return transactionRepository.findAllByUserId(user);
    }

    public String deleteTransaction(Long id) {
        boolean transactionExists = transactionRepository.findById(id).isPresent();
        Transaction getTransaction = transactionRepository.findById(id).get();
        if (!transactionExists){
            return "Account Does not exist";
        }
        transactionRepository.delete(getTransaction);
        return "transaction deleted";
    }
}
