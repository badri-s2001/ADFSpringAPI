package com.badri.springapi.controller;


import com.badri.springapi.entity.Statement;
import com.badri.springapi.entity.Transaction;
import com.badri.springapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class TransactionController {


    @Autowired
    private TransactionService service;

    @GetMapping("/transaction")
    public ResponseEntity<Object> showTransaction(){
        return service.getTransactions();
    }

    @GetMapping("/statement")
    public ResponseEntity<Object> findTransactionByAccountNumber(@RequestBody Statement statement) throws ParseException {
        return service.getTransactionsByAccountNumber(statement);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction){
        return service.saveTransaction(transaction);
    }

}
