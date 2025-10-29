package com.project.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String transactionId;
    private int memberId;
    private int bookId;
    private String transactionType; // "borrow" or "return"
    private LocalDate transactionDate;

    @PrePersist
    public void generateTransactionID(){
        if(this.transactionId == null || this.transactionId.isEmpty()){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String dateTime = dtf.format(LocalDateTime.now());
            String transactionTypeinID;
            if (transactionType.equalsIgnoreCase("issue")){
                transactionTypeinID = "ISS";
            }else{
                transactionTypeinID = "RET";
            }
            this.transactionId = transactionTypeinID + dateTime + "-M" + memberId + "-B" + bookId;

        }


    }



    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
