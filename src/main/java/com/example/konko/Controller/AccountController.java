package com.example.konko.Controller;


import com.example.konko.Account.Account;
import com.example.konko.Account.AccountService;
import com.example.konko.Account.Model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/app/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody AccountModel accountModel){
        return accountService.createAccount(accountModel);
    }
}
