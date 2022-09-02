package com.example.konko.account.dto;

import com.example.konko.account.AccountType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class AccountDto {
    private String accountName;
    private String accountNumber;
    private boolean active;
    private Double balance;
    private AccountType accountType;
    private Long userId;
}
