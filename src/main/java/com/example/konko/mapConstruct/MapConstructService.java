package com.example.konko.mapConstruct;

import com.example.konko.account.Account;
import com.example.konko.account.repository.AccountRepository;
import com.example.konko.account.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapConstructService implements MapStructMapper{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MapStructMapper mapper;
    public void updateAccount(AccountDto dto) {
        Account account = accountRepository.findAccountByAccountNumber(dto.getAccountNumber()).get();
        mapper.updateAccountFromDto(dto,account);
        accountRepository.save(account);
    }

    @Override
    public void updateAccountFromDto(AccountDto dto, Account account) {
        return;
    }
}
