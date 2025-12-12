package com.project.lms.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "member_books")
public class Member_Books{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Optional but recommended

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "quantity")
    private int quantity;
    // Constructors
    public Member_Books() {}

    public Member_Books(Long memberId, Long bookId) {
        this.memberId = memberId;
        this.bookId = bookId;

    }
    public Member_Books(Long memberId, Long bookId, int quantity) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}






