package com.project.lms.controller;

import com.project.lms.model.Transaction;
import com.project.lms.model.TransactionRequest;
import com.project.lms.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public void getTransactions() {
        transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionID}")
    public void getTransactionDetails(@PathVariable String transactionID) {
        transactionService.getTransactionById(transactionID);
    }

    @PostMapping
    public void addTransaction(@RequestBody TransactionRequest transactionReq) {
        transactionService.addTransaction(transactionReq);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable String transactionId) {
        transactionService.deleteTransaction(transactionId);
    }
}
