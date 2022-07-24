package com.example.konko.Account;

import com.example.konko.Account.Model.AccountModel;
import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    public Account createAccount(AccountModel accountModel){
        //Check if account exist
        boolean accountExist = accountRepository.findAccountByAccountNumber(accountModel.getAccountNumber()).isPresent();
        //Check if account exists
        if (accountExist){
            throw new IllegalStateException("Account Already Exist");
        }
        //  Check if user already Exist
        boolean userExist = userRepository.findUserById(accountModel.getUserId()).isPresent();
        if (!userExist){
            throw new IllegalStateException("User Not found");
        }

        User user = userRepository.findUserById(accountModel.getUserId()).get();
        return saveAccount(
                new Account(
                        accountModel.getAccountName(),
                        accountModel.getAccountNumber(),
                        LocalDateTime.now(),
                        accountModel.isActive(),
                        accountModel.getBalance(),
                        accountModel.getAccountType(),
                        user
                )
        );

    }

    private Account saveAccount(Account account) {

        accountRepository.save(account);

        return account;
    }
}
