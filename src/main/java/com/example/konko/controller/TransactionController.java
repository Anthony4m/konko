package com.example.konko.controller;

import com.example.konko.transactions.Transaction;
import com.example.konko.transactions.dto.TransactionDto;
import com.example.konko.transactions.service.TransactionService;
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

@RestController
@RequestMapping("api/app/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public String createTransaction(@RequestBody TransactionDto transactionDto){
        return transactionService.createTransaction(transactionDto);
    }
    @GetMapping(value = "user/{id}")
    public List<Transaction> getUserTransactions(@PathVariable("id") Long id){
        return transactionService.getTransaction(id);
    }
    @DeleteMapping(value = "transaction/delete{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") Long id){
        return transactionService.deleteTransaction(id);
    }
//    @PutMapping(value = "/transaction/update/{transactionId}")
//    public String updateTransaction(@PathVariable("transactionId") Long id, @RequestBody TransactionDto transactionDto){
//        transactionService.updateTransaction(id, transactionDto);
//    }
}
