package com.example.konko.controller;


import com.example.konko.account.Account;
import com.example.konko.account.service.AccountService;
import com.example.konko.account.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/app/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public String createAccount(@RequestBody AccountDto accountModel){
        return accountService.createAccount(accountModel).toString();
    }
    @GetMapping(value = "user/{id}")
    public List<Account> getUserAccounts(@PathVariable("id") String id){
        return accountService.getAccount(Long.valueOf(id));
    }
    @GetMapping(value = "accountnumber/{accountNumber}")
    public String getAccountWithAccountNumber(@PathVariable("accountNumber") String accountNumber){
        return accountService.getAccount(accountNumber);
    }
    @DeleteMapping(value = "/user/delete/{accountNumber}")
    public String deleteAccount(@PathVariable("accountNumber") String accountNumber){
        return accountService.deleteAccount(accountNumber);
    }
    @PutMapping(value = "/user/update/{accountId}/{newAccountBalance}")
    public String updateAccountBalance(@PathVariable("accountId") String accountId, @PathVariable("newAccountBalance") Double newAccountBalance){
        return accountService.updateAccountBalance(Long.valueOf(accountId),newAccountBalance);
    }

    @PutMapping("/update")
    public Optional<List<Account>> updateAccount(@RequestBody AccountDto accountModel){
        return accountService.updateAccount(accountModel);
    }
}
