package com.example.konko.Account.Model;

import com.example.konko.Account.AccountType;
import com.example.konko.User.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountModel {
    private String accountName;
    private String accountNumber;
    private boolean active;
    private Double balance;
    private AccountType accountType;
    private Long userId;
}
