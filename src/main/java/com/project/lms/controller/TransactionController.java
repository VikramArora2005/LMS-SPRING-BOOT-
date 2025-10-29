package com.project.lms.controller;

import com.project.lms.model.Transaction;
import com.project.lms.repo.TransactionRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/transactions")
public class TransactionController {


    private final TransactionRepo transactionRepo;

    public TransactionController(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    @GetMapping("/{transactionID}")
    public Transaction getTransactionDetails(@PathVariable String transactionID) {
        for (Transaction transaction : transactionRepo.findAll()) {
            if (transaction.getTransactionId().equals(transactionID)) {
                return transaction;
            }
        }
        return null;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionRepo.save(transaction);
    }
    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable String transactionId) {
        for (Transaction transaction : transactionRepo.findAll()) {
            if  (transaction.getTransactionId().equals(transactionId)) {
                transactionRepo.delete(transaction);
            }
        }
    }

    @PutMapping
    public Transaction updateTransaction(@RequestBody Transaction transaction) {
        return transactionRepo.save(transaction);
    }

}
