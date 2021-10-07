package com.badri.springapi.service;

import com.badri.springapi.entity.Account;
import com.badri.springapi.entity.Statement;
import com.badri.springapi.entity.Transaction;
import com.badri.springapi.repository.AccountRepository;
import com.badri.springapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public ResponseEntity<Object> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        return new ResponseEntity<>(transactions, HttpStatus.FOUND);
    }

    public ResponseEntity<Object> getTransactionsByAccountNumber(Statement statement) throws ParseException {

        long account_number = statement.getAccountNumber();
        Date start_date = new SimpleDateFormat("dd/MM/yyyy").parse(statement.getStartDate());
        Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse(statement.getEndDate());

        HashMap<String, Object> response = new HashMap<>();

        String sDate1="31/12/2015";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        Date date = new Date();

        if(start_date.before(date1) || end_date.after(date)){
            response.put("status", "failure");
            response.put("reason", "Invalid date entered");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Account existingAccount = accountRepository.findById(account_number).orElse(null);
        if(existingAccount == null){

            response.put("accountNumber", account_number);
            response.put("status", "failure");
            response.put("reason", "Account with account number does not exist");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


        List<Transaction> transactions = transactionRepository.findAll();

        List<Transaction> result = new ArrayList<Transaction>();
        
        for(Transaction transaction : transactions){
            if(transaction.getAccountNumber() == account_number && (!start_date.after(transaction.getTransactionDate()) && !end_date.before(transaction.getTransactionDate()))){
                result.add(transaction);
            }
        }

        if(result.size() == 0){
            response.put("accountNumber", account_number);
            response.put("status", "failure");
            response.put("reason", "No transactions made with account");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


    public ResponseEntity<Object> saveTransaction(Transaction transaction) {
        long accountNumber = transaction.getAccountNumber();
        int amount = transaction.getTransactionAmount();
        String type = transaction.getTransactionType();
        Date date = new Date();

        HashMap<String, Object> response = new HashMap<>();
        response.put("accountNumber", accountNumber);

        Account existingAccount = accountRepository.findById(accountNumber).orElse(null);

        if (existingAccount == null) {


            response.put("status", "failure");
            response.put("reason", "Account with account number does not exist");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            int balance = existingAccount.getBalance();
            transaction.setTransactionType(type);
            transaction.setTransactionDate(date);
            if(Objects.equals(type, "deposit")){
                balance = balance+amount;
                existingAccount.setBalance(balance);

                transaction.setTransactionStatus("success");
                accountRepository.save(existingAccount);
                transactionRepository.save(transaction);

                response.put("type", "deposit");
                response.put("amount", amount);
                response.put("status", "success");

                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

            }
            else{
                if(balance < amount){
                    transaction.setTransactionStatus("failure");
                    transactionRepository.save(transaction);

                    response.put("type", "withdraw");
                    response.put("amount", amount);
                    response.put("status", "failure");
                    response.put("reason", "Insufficient funds");

                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                else{
                    balance = balance-amount;
                    existingAccount.setBalance(balance);

                    transaction.setTransactionStatus("success");
                    accountRepository.save(existingAccount);
                    transactionRepository.save(transaction);

                    response.put("type", "withdraw");
                    response.put("amount", amount);
                    response.put("status", "success");

                    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
                }

            }
        }


    }

}
