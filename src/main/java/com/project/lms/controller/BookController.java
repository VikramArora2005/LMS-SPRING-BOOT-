package com.project.lms.controller;

import com.project.lms.model.Book;
import com.project.lms.repo.BookRepo;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/books")
public class BookController {


    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }
    @DeleteMapping
    public void deleteBook(@RequestBody long bookId) {
        bookRepo.deleteById(bookId);

    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }

}
