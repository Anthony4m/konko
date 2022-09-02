package com.example.konko.transactions.dto;

import com.example.konko.User.User;
import com.example.konko.account.Account;
import com.example.konko.transactions.Catergory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private Double amount;
    private String description;
    private Catergory catergory;
    private String recipient;
    private LocalDateTime createdAt;
    private Long userId;
    private Long accountId;
}
