package com.badri.springapi.service;


import com.badri.springapi.entity.Account;
import com.badri.springapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public ResponseEntity<Object> saveAccount(Account account) {

        HashMap<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());

        Account existingAccount = repository.findById(account.getAccountNumber()).orElse(null);

        if (existingAccount == null) {
            repository.save(account);
            response.put("status", "success");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("status", "failure");
            response.put("reason", "Account with account number already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Object> getAccounts() {
        List<Account> accounts = repository.findAll();

        return new ResponseEntity<>(accounts, HttpStatus.FOUND);
    }

    public ResponseEntity<Object> getAccountByAccountNumber(long account_number) {
        Account existingAccount = repository.findById(account_number).orElse(null);

        if (existingAccount != null) {
            return new ResponseEntity<>(existingAccount, HttpStatus.FOUND);
        } else {
            HashMap<String, Object> response = new HashMap<>();
            response.put("accountNumber", account_number);
            response.put("status", "failure");
            response.put("reason", "Account with account number does not exist");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


    }

    public ResponseEntity<Object> deleteAccount(long account_number) {

        Account existingAccount = repository.findById(account_number).orElse(null);
        HashMap<String, Object> response = new HashMap<>();
        response.put("accountNumber", account_number);

        if (existingAccount != null) {
            repository.deleteById(account_number);
            response.put("status", "deleted");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            response.put("status", "failure");
            response.put("reason", "Account with given account number does not exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> replaceAccount(Account account) {
        Account existingAccount = repository.findById(account.getAccountNumber()).orElse(null);
        HashMap<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());

        if (existingAccount != null) {
            existingAccount.setFirstName(account.getFirstName());

            existingAccount.setLastName(account.getLastName());

            existingAccount.setCity(account.getCity());

            existingAccount.setAadharNumber(account.getAadharNumber());

            existingAccount.setPanNumber(account.getPanNumber());

            existingAccount.setPhoneNumber(account.getPhoneNumber());

            existingAccount.setIfsc(account.getIfsc());

            repository.save(existingAccount);

            response.put("status", "updated");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        } else {
            response.put("status", "failure");
            response.put("reason", "Account with given account number does not exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateAccount(Account account) {
        Account existingAccount = repository.findById(account.getAccountNumber()).orElse(null);
        HashMap<String, Object> response = new HashMap<>();
        response.put("accountNumber", account.getAccountNumber());

        if (existingAccount != null) {
            if (account.getFirstName() != null) {
                existingAccount.setFirstName(account.getFirstName());
            }


            if (account.getLastName() != null) {
                existingAccount.setLastName(account.getLastName());
            }


            if (account.getCity() != null) {
                existingAccount.setCity(account.getCity());
            }


            if (account.getAadharNumber() != 0) {
                existingAccount.setAadharNumber(account.getAadharNumber());
            }


            if (account.getPanNumber() != null) {
                existingAccount.setPanNumber(account.getPanNumber());
            }


            if (account.getPhoneNumber() != 0) {
                existingAccount.setPhoneNumber(account.getPhoneNumber());
            }

            if (account.getIfsc() != null) {
                existingAccount.setIfsc(account.getIfsc());
            }

            repository.save(existingAccount);

            response.put("status", "updated");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            response.put("status", "failure");
            response.put("reason", "Account with given account number does not exist.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


    }


}
