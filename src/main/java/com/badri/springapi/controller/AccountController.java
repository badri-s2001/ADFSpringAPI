package com.badri.springapi.controller;

import com.badri.springapi.entity.Account;
import com.badri.springapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountController {

    @Autowired
    private AccountService service;


    @PostMapping("/accounts")
    public ResponseEntity<Object> addAccount(@RequestBody Account account){
        return service.saveAccount(account);
    }

    @GetMapping("/accounts")
    public ResponseEntity<Object> findAllAccounts(){
        return service.getAccounts();
    }

    @GetMapping("/accounts/{account_number}")
    public ResponseEntity<Object> findAccountByAccountNumber(@PathVariable long account_number){
        return service.getAccountByAccountNumber(account_number);
    }

    @PutMapping("/accounts")
    public ResponseEntity<Object> replaceAccount(@RequestBody Account account){
        return service.replaceAccount(account);
    }

    @PatchMapping("/accounts")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account){
        return service.updateAccount(account);
    }

    @DeleteMapping("accounts/{account_number}")
    public ResponseEntity<Object> deleteAccount(@PathVariable long account_number){
        return service.deleteAccount(account_number);
    }




}
