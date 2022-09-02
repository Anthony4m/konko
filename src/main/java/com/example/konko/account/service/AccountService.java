package com.example.konko.account.service;

import com.example.konko.account.Account;
import com.example.konko.account.repository.AccountRepository;
import com.example.konko.account.dto.AccountDto;
import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.mapConstruct.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapStructMapper mapper;
    public String createAccount(AccountDto accountModel){
        //Check if account exist
        boolean accountExist = accountRepository.findAccountByAccountNumber(accountModel.getAccountNumber()).isEmpty();
        //Check if account exists
        if (!accountExist){
            return  "Account Already Exist";
        }
        //  Check if user already Exist
        boolean userExist = userRepository.findUserById(accountModel.getUserId()).isPresent();
        if (!userExist){
            return "User Not found";
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

    private String saveAccount(Account account) {

        accountRepository.save(account);

        return account.toString();
    }

    public List<Account> getAccount(Long id) {
        User user = userRepository.findUserById(id).get();
//        boolean accountExists = accountRepository.findAccountsByUserId(user).isPresent();

//        if (!accountExists){
//            throw new IllegalStateException("User has no accounts");
//        }
        return accountRepository.findAllAccountByUserId(user);
    }

    public String getAccount(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber).get();
        return account.toString();
    }

    public String deleteAccount(String accountNumber) {
//        find if account exist
        boolean accountExists = accountRepository.findAccountByAccountNumber(accountNumber).isPresent();
        Account getAccount = accountRepository.findAccountByAccountNumber(accountNumber).get();
        if (!accountExists){
            return "Account Does not exist";
        }
        accountRepository.delete(getAccount);
        return "Account Deleted";
    }

    public String updateAccountBalance(Long accountId, Double newAccountBalance) {
        //find if account exist
        Account accountExists = accountRepository.findAccountById(accountId).get();
        accountRepository.updateAccountBalance(accountId,newAccountBalance);
        return accountRepository.findAccountById(accountId).get().toString();
    }

    public Optional<List<Account>> updateAccount(AccountDto accountModel) {
        //Check if account exist
//        boolean accountExist = accountRepository.findAccountByAccountNumber(accountModel.getAccountNumber()).getAccountNumber().isEmpty();
//        //Check if account exists
//        if (!accountExist){
//            throw new IllegalStateException("Account Already Exist");
//        }
        //  Check if user already Exist
        boolean userExist = userRepository.findUserById(accountModel.getUserId()).isPresent();
        if (!userExist){
            throw new IllegalStateException("User Not found");
        }

        Account account = accountRepository.findAccountById(accountModel.getUserId()).get();
        User user = userRepository.findUserById(accountModel.getUserId()).get();
        mapper.updateAccountFromDto(accountModel,account);
        accountRepository.save(account);
        return Optional.of(accountRepository.findAllAccountByUserId(user));
    }
}
