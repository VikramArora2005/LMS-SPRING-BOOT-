package com.project.lms.model;

public class MemberBookDTO {
    private Book book;
    private int quantity;

    public MemberBookDTO(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }



    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }
}
